package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private UUID id;

    private String name;

    private String theme;

    private String address;

    private Integer guests;

    @JsonProperty("date_time")
    private String dateTime;

    private String color;

    @JsonProperty("organization_id")
    private UUID organizationId;
}
