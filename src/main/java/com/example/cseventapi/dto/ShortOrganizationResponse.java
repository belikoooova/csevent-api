package com.example.cseventapi.dto;

import com.example.cseventapi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortOrganizationResponse {
    private UUID id;
    private String title;
    private Role role;
}
