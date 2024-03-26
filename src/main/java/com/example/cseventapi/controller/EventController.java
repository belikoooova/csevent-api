package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("organizations/{organizationId}/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<ShortEventResponse> getAll(@PathVariable UUID organizationId) {
        return eventService.getAll(organizationId);
    }

    @GetMapping("/{eventId}")
    public Event get(@PathVariable UUID eventId, @PathVariable UUID organizationId) {
        return eventService.get(eventId);
    }

    @PostMapping
    public Event create(@PathVariable UUID organizationId, @RequestBody @Valid CreateOrUpdateEventRequest request) {
        return eventService.create(organizationId, request);
    }

    @PutMapping("/{eventId}")
    public Event update(
            @PathVariable UUID eventId,
            @RequestBody @Valid CreateOrUpdateEventRequest request,
            @PathVariable UUID organizationId
    ) {
        return eventService.update(eventId, request);
    }

    @DeleteMapping("/{eventId}")
    public Event delete(@PathVariable UUID eventId, @PathVariable UUID organizationId) {
        return eventService.delete(eventId);
    }

    @GetMapping("/{eventId}/organizers")
    public List<ShortUserResponse> getOrganizers(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId
    ) {
        return eventService.getOrganizers(eventId, organizationId);
    }

    @PostMapping("/{eventId}/organizers")
    public void addOrganizers(
            @PathVariable UUID eventId,
            @RequestBody @Valid AddOrganizersRequest request,
            @PathVariable UUID organizationId) {
        eventService.addOrganizers(eventId, request);
    }

    @DeleteMapping("/{eventId}/organizers/{organizerId}")
    public void deleteOrganizer(
            @PathVariable UUID eventId,
            @PathVariable UUID organizerId,
            @PathVariable UUID organizationId
    ) {
        eventService.deleteOrganizer(eventId, organizerId);
    }
}
