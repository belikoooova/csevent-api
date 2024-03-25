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
    public List<ShortEventResponse> getAll(OrganizationIdRequest request) {
        return eventDao.findAllByOrganizationId(request.getOrganizationId()).stream()
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
    public Event get(UUID id) {
        return eventDataModelToEventDtoMapper.map(eventDao.findById(id).get());
    }

    @Override
    @Transactional
    public Event create(CreateOrUpdateEventRequest request) {
        Event event = Event.builder()
                .name(request.getName())
                .address(request.getAddress())
                .organizationId(request.getOrganizationId())
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
    public Event update(UUID id, CreateOrUpdateEventRequest request) {
        Event event = eventDataModelToEventDtoMapper.map(eventDao.findById(id).get());

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
    public Event delete(UUID id) {
        Event event = eventDataModelToEventDtoMapper.map(eventDao.findById(id).get());

        eventDao.deleteById(id);

        return event;
    }

    @Override
    @Transactional
    public List<ShortUserResponse> getOrganizers(UUID eventId, OrganizationIdRequest request) {
        List<ShortUserResponse> allMembers = organizationService.getAllMembers(request.getOrganizationId());

        return allMembers.stream()
                .filter(response -> response.getTags().stream()
                        .anyMatch(tag -> tag.getEventId().equals(eventId)))
                .toList();
    }

    @Override
    @Transactional
    public List<ShortUserResponse> getNotOrganizers(UUID eventId, SearchNotOrganizerRequest request) {
        List<ShortUserResponse> allMembers = organizationService.getAllMembers(request.getOrganizationId());

        return allMembers.stream()
                .filter(response -> response.getTags().stream()
                        .noneMatch(tag -> tag.getEventId().equals(eventId)))
                .filter(response -> response.getName().contains(
                        request.getSubstring() == null
                        ? ""
                        : request.getSubstring()
                ))
                .toList();
    }

    @Override
    @Transactional
    public void addOrganizer(UUID eventId, AddOrganizerRequest request) {
        entityManager.createNativeQuery(
                "insert into user_event (user_id, event_id) " +
                        "values (:userId, :eventId)"
                ).setParameter("eventId", eventId)
                .setParameter("userId", request.getOrganizerId())
                .executeUpdate();
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
