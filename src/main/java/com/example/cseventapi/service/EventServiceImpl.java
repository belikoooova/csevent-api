package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.mapper.EventDataModelToEventDtoMapper;
import com.example.cseventapi.mapper.EventDtoToEventDataModelMapper;
import com.example.cseventapi.repository.EventDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;
    private final EntityManager entityManager;
    private final OrganizationService organizationService;
    private final EventDataModelToEventDtoMapper eventDataModelToEventDtoMapper;
    private final EventDtoToEventDataModelMapper eventDtoToEventDataModelMapper;

    @Override
    @Transactional
    public List<ShortEventResponse> getAll(UUID organizationId) {
        return eventDao.findAllByOrganizationId(organizationId).stream()
                .map(event -> ShortEventResponse.builder()
                        .name(event.getName())
                        .color(event.getColor())
                        .address(event.getAddress())
                        .id(event.getId())
                        .dateTime(event.getDateTime())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public Event get(UUID eventId) {
        return eventDataModelToEventDtoMapper.map(eventDao.findById(eventId).get());
    }

    @Override
    @Transactional
    public Event create(UUID organizationId, CreateOrUpdateEventRequest request) {
        Event event = Event.builder()
                .name(request.getName())
                .address(request.getAddress())
                .organizationId(organizationId)
                .guests(request.getGuests() == null ? 0 : request.getGuests())
                .dateTime(request.getDateTime())
                .color(request.getColor())
                .build();

        return eventDataModelToEventDtoMapper.map(
                eventDao.save(eventDtoToEventDataModelMapper.map(event))
        );
    }

    @Override
    @Transactional
    public Event update(UUID eventId, CreateOrUpdateEventRequest request) {
        Event event = eventDataModelToEventDtoMapper.map(eventDao.findById(eventId).get());

        event.setAddress(request.getAddress());
        event.setGuests(request.getGuests() == null ? 0 : request.getGuests());
        event.setDateTime(request.getDateTime());
        event.setColor(request.getColor());

        return eventDataModelToEventDtoMapper.map(
                eventDao.save(eventDtoToEventDataModelMapper.map(event))
        );
    }

    @Override
    @Transactional
    public Event delete(UUID eventId) {
        Event event = eventDataModelToEventDtoMapper.map(eventDao.findById(eventId).get());

        eventDao.deleteById(eventId);

        return event;
    }

    @Override
    @Transactional
    public List<ShortUserResponse> getOrganizers(UUID eventId, UUID organizationId) {
        List<ShortUserResponse> allMembers = organizationService.getAllMembers(organizationId);

        return allMembers.stream()
                .filter(response -> response.getTags().stream()
                        .anyMatch(tag -> tag.getEventId().equals(eventId)))
                .toList();
    }

    @Override
    @Transactional
    public void addOrganizers(UUID eventId, AddOrganizersRequest request) {
        request.getOrganizerIds().forEach(
                organizerId -> entityManager.createNativeQuery(
                                "insert into user_event (user_id, event_id) " +
                                         "values (:userId, :eventId) " +
                                         "on conflict (user_id, event_id) do nothing"
                        ).setParameter("eventId", eventId)
                        .setParameter("userId", organizerId)
                        .executeUpdate()
        );
    }

    @Override
    public void deleteOrganizer(UUID eventId, UUID userId) {
        entityManager.createNativeQuery(
                        "delete from user_event " +
                                "where user_id = :userId and event_id = :eventId"
                ).setParameter("eventId", eventId)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
