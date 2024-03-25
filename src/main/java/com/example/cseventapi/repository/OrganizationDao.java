package com.example.cseventapi.repository;

import com.example.cseventapi.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationDao extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByNickname(String nickname);

    boolean existsByNickname(String nickname);
}
