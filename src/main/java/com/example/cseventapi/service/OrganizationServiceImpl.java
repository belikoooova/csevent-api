package com.example.cseventapi.service;

import com.example.cseventapi.dto.CreateOrganizationRequest;
import com.example.cseventapi.dto.Organization;
import com.example.cseventapi.dto.SignInOrganizationRequest;
import com.example.cseventapi.entity.Role;
import com.example.cseventapi.exception.OrganizationWithSuchNicknameAlreadyExists;
import com.example.cseventapi.exception.OrganizationWithSuchNicknameNotExist;
import com.example.cseventapi.exception.OrganizationWrongSecretCode;
import com.example.cseventapi.mapper.OrganizationDataModelToOrganizationDtoMapper;
import com.example.cseventapi.mapper.OrganizationDtoToOrganizationDataModelMapper;
import com.example.cseventapi.repository.OrganizationDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final UserService userService;
    private final OrganizationDao organizationDao;
    private final OrganizationDataModelToOrganizationDtoMapper organizationDataModelToOrganizationDtoMapper;
    private final OrganizationDtoToOrganizationDataModelMapper organizationDtoToOrganizationDataModelMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Organization create(CreateOrganizationRequest request) {
        if (organizationDao.existsByNickname(request.getNickname())) {
            throw new OrganizationWithSuchNicknameAlreadyExists();
        }

        Organization organization = Organization.builder()
                .title(request.getTitle())
                .nickname(request.getNickname())
                .secretCode(request.getSecretCode())
                .build();

        Organization savedOrganization = organizationDataModelToOrganizationDtoMapper.map(
                organizationDao.save(
                        organizationDtoToOrganizationDataModelMapper.map(organization)
                )
        );

        addMember(userService.getCurrentUser().getId(), savedOrganization.getId(), Role.OWNER);

        return savedOrganization;
    }

    @Override
    @Transactional
    public Organization signIn(SignInOrganizationRequest request) {
        if (!organizationDao.existsByNickname(request.getNickname())) {
            throw new OrganizationWithSuchNicknameNotExist();
        }

        Organization organization = organizationDataModelToOrganizationDtoMapper.map(
                organizationDao.findByNickname(request.getNickname()).get()
        );

        if (!organization.getSecretCode().equals(request.getSecretCode())) {
            throw new OrganizationWrongSecretCode();
        }

        if (isNotMember(userService.getCurrentUser().getId(), organization.getId())) {
            addMember(userService.getCurrentUser().getId(), organization.getId(), Role.MEMBER);
        }

        return organization;
    }

    private boolean isNotMember(UUID userId, UUID organizationId) {
        Long result = (Long) entityManager.createNativeQuery(
                        "select count(*) from user_organization " +
                                 "where organization_id = :organizationId and user_id = :userId"
                ).setParameter("userId", userId)
                .setParameter("organizationId", organizationId)
                .getSingleResult();
        return result == 0;
    }

    private void addMember(UUID userId, UUID organizationId, Role role) {
        entityManager.createNativeQuery(
                "insert into user_organization (user_id, organization_id, role) " +
                        "values (:userId, :organizationId, :role)"
                )
                .setParameter("userId", userId)
                .setParameter("organizationId", organizationId)
                .setParameter("role", role.name())
                .executeUpdate();
    }
}
