package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.EventServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventServiceImpl eventService;

    @GetMapping
    public List<ShortEventResponse> getAll(OrganizationIdRequest request) {
        return eventService.getAll(request);
    }

    @GetMapping("/{id}")
    public Event get(@PathVariable UUID id) {
        return eventService.get(id);
    }

    @PostMapping
    public Event create(CreateOrUpdateEventRequest request) {
        return eventService.create(request);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable UUID id, CreateOrUpdateEventRequest request) {
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
}
