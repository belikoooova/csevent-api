package com.example.cseventapi.dto;

import com.example.cseventapi.entity.CocktailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCocktailResponse {
    private UUID id;

    private String name;

    private CocktailType type;
}
