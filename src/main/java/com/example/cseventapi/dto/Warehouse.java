package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    private UUID id;

    private String name;

    private String address;

    @JsonProperty("organization_id")
    private UUID organizationId;
}
