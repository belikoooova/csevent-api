package com.example.cseventapi.controller;

import com.example.cseventapi.dto.CreateOrganizationRequest;
import com.example.cseventapi.dto.Organization;
import com.example.cseventapi.dto.SignInOrganizationRequest;
import com.example.cseventapi.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/create")
    public Organization create(@RequestBody @Valid CreateOrganizationRequest request) {
        return organizationService.create(request);
    }

    @PostMapping("/sign-in")
    public Organization signIn(@RequestBody @Valid SignInOrganizationRequest request) {
        return organizationService.signIn(request);
    }
}
