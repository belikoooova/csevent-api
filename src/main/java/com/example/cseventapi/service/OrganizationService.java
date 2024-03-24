package com.example.cseventapi.service;

import com.example.cseventapi.dto.CreateOrganizationRequest;
import com.example.cseventapi.dto.Organization;
import com.example.cseventapi.dto.ShortUserResponse;
import com.example.cseventapi.dto.SignInOrganizationRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {
    @Transactional
    Organization create(CreateOrganizationRequest request);

    @Transactional
    Organization signIn(SignInOrganizationRequest request);

    @Transactional
    List<ShortUserResponse> getAllMembers(UUID organizationId);
}
