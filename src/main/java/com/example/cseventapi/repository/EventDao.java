package com.example.cseventapi.repository;

import com.example.cseventapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventDao extends JpaRepository<Event, UUID> {
    List<Event> findAllByOrganizationId(UUID organizationId);
}
