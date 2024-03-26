package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface EventService {
    @Transactional
    List<ShortEventResponse> getAll(UUID organizationId);

    @Transactional
    Event get(UUID eventId);

    @Transactional
    Event create(UUID organizationId, CreateOrUpdateEventRequest request);

    @Transactional
    Event update(UUID eventId, CreateOrUpdateEventRequest request);

    @Transactional
    Event delete(UUID eventId);

    @Transactional
    List<ShortUserResponse> getOrganizers(UUID eventId, UUID organizationId);

    @Transactional
    void addOrganizers(UUID eventId, AddOrganizersRequest request);

    @Transactional
    void deleteOrganizer(UUID eventId, UUID organizerId);
}
