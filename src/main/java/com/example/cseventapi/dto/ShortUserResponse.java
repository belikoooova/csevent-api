package com.example.cseventapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUserResponse {
    private UUID id;
    private String name;
    private String color;
    private List<EventTag> tags;
}
