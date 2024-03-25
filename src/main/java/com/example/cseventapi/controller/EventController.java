package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<ShortEventResponse> getAll(@RequestBody @Valid OrganizationIdRequest request) {
        return eventService.getAll(request);
    }

    @GetMapping("/{id}")
    public Event get(@PathVariable UUID id) {
        return eventService.get(id);
    }

    @PostMapping("/create")
    public Event create(@RequestBody @Valid CreateOrUpdateEventRequest request) {
        return eventService.create(request);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable UUID id, @RequestBody @Valid CreateOrUpdateEventRequest request) {
        return eventService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Event delete(@PathVariable UUID id) {
        return eventService.delete(id);
    }

    @GetMapping("/{id}/organizers")
    public List<ShortUserResponse> getOrganizers(
            @PathVariable UUID id,
            @RequestBody @Valid OrganizationIdRequest request
    ) {
        return eventService.getOrganizers(id, request);
    }

    @GetMapping("/{id}/not-organizers")
    public List<ShortUserResponse> getNotOrganizers(
            @PathVariable UUID id,
            @RequestBody @Valid SearchNotOrganizerRequest request
    ) {
        return eventService.getNotOrganizers(id, request);
    }

    @PostMapping("/{id}/organizers")
    public void addOrganizer(@PathVariable UUID id, @RequestBody @Valid AddOrganizerRequest request) {
        eventService.addOrganizer(id, request);
    }

    @DeleteMapping("/{eventId}/organizers/{organizerId}")
    public void deleteOrganizer(@PathVariable UUID eventId, @PathVariable UUID organizerId) {
        eventService.deleteOrganizer(eventId, organizerId);
    }
}
