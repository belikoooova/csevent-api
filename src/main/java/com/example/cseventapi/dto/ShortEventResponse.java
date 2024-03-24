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
public class ShortEventResponse {
    private UUID id;

    private String name;

    private String address;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    private String color;
}
