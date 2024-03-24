package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
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

import java.util.*;

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

    @Override
    @Transactional
    public List<ShortUserResponse> getAllMembers(UUID organizationId) {
        List<Object[]> usersInOrganization = entityManager.createNativeQuery(
                        "select u.id, u.name, u.color from users u " +
                                "join user_organization uo on uo.user_id = u.id " +
                                "where uo.organization_id = :organizationId"
                ).setParameter("organizationId", organizationId)
                .getResultList();

        List<Object[]> objects = entityManager.createNativeQuery(
                        "select u.id, e.id, e.name, e.color from users u " +
                                "join user_event ue on ue.user_id = u.id " +
                                "join events e on ue.event_id = e.id " +
                                "where e.organization_id = :organizationId"
                ).setParameter("organizationId", organizationId)
                .getResultList();

        return mapToListShortUserResponse(usersInOrganization, objects);
    }

    private List<ShortUserResponse> mapToListShortUserResponse(List<Object[]> usersInOrganization, List<Object[]> objects) {
        Map<UUID, ShortUserResponse> usersMap = new HashMap<>();

        usersInOrganization.stream().forEach(
                o -> {
                    UUID userId = (UUID) o[0];
                    usersMap.put(userId, ShortUserResponse.builder()
                            .id(userId)
                            .name((String) o[1])
                            .color((String) o[2])
                            .tags(new ArrayList<>())
                            .build());
                }
        );

        for (Object[] row : objects) {
            UUID userId = (UUID) row[0];
            UUID eventId = (UUID) row[1];
            String eventName = (String) row[2];
            String eventColor = (String) row[3];

            EventTag eventTag = new EventTag(eventId, eventName, eventColor);

            usersMap.get(userId).getTags().add(eventTag);
        }

        return usersMap.values().stream().toList();
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
