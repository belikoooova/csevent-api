package com.example.cseventapi.dto;

import com.example.cseventapi.entity.CocktailType;
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
public class Cocktail {
    private UUID id;

    private String name;

    private CocktailType type;

    @JsonProperty("event_id")
    private UUID eventId;
}
