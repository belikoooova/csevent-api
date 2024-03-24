package com.example.cseventapi.service;

import com.example.cseventapi.dto.ShortOrganizationResponse;
import com.example.cseventapi.dto.UpdateUserDataRequest;
import com.example.cseventapi.entity.Organization;
import com.example.cseventapi.entity.Role;
import com.example.cseventapi.entity.User;
import com.example.cseventapi.exception.UserWithSuchEmailAlreadyExists;
import com.example.cseventapi.repository.UserDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public User create(User user) {
        if (userDao.existsByEmail(user.getEmail())) {
            throw new UserWithSuchEmailAlreadyExists();
        }

        return save(user);
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    @Override
    @Transactional
    public User getCurrentUser() {
        return getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    @Transactional
    public User updateUserData(UpdateUserDataRequest request) {
        User user = getCurrentUser();
        user.setName(request.getName());
        user.setColor(request.getColor());
        return userDao.save(user);
    }

    @Override
    @Transactional
    public List<ShortOrganizationResponse> getListOfOrganizations() {
        User user = getCurrentUser();
        List<Organization> organizations = getUsersOrganizations(user.getId());

        return organizations.stream()
                .map(o -> ShortOrganizationResponse.builder()
                            .id(o.getId())
                            .title(o.getTitle())
                            .role(getUserRoleInOrganization(user.getId(), o.getId()))
                            .build())
                .toList();
    }

    private User save(User user) {
        return userDao.save(user);
    }

    private List<Organization> getUsersOrganizations(UUID userId) {
        @SuppressWarnings("unchecked")
        List<Organization> organizations = entityManager.createNativeQuery(
                "select * from organizations o " +
                        "where o.id in (select uo.organization_id from user_organization uo " +
                        "where uo.user_id = :userId)",
                        Organization.class
                ).setParameter("userId", userId)
                .getResultList();
        return organizations;
    }

    private Role getUserRoleInOrganization(UUID userId, UUID organizationId) {
        String roleName = ((String)entityManager.createNativeQuery(
                "select role from user_organization " +
                        "where organization_id = :organizationId and user_id = :userId"
                ).setParameter("userId", userId)
                .setParameter("organizationId", organizationId)
                .getSingleResult()).toUpperCase();
        return Role.valueOf(roleName);
    }
}
