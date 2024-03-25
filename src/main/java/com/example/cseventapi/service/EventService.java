package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface EventService {
    @Transactional
    List<ShortEventResponse> getAll(OrganizationIdRequest request);

    @Transactional
    Event get(UUID id);

    @Transactional
    Event create(CreateOrUpdateEventRequest request);

    @Transactional
    Event update(UUID id, CreateOrUpdateEventRequest request);

    @Transactional
    Event delete(UUID id);

    @Transactional
    List<ShortUserResponse> getOrganizers(UUID eventId, OrganizationIdRequest request);

    @Transactional
    List<ShortUserResponse> getNotOrganizers(UUID eventId, SearchNotOrganizerRequest request);

    @Transactional
    void addOrganizer(UUID eventId, AddOrganizerRequest request);

    @Transactional
    void deleteOrganizer(UUID eventId, UUID organizerId);
}
