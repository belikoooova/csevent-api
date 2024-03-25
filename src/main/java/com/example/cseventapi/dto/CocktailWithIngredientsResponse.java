package com.example.cseventapi.dto;

import com.example.cseventapi.entity.CocktailType;
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
public class CocktailWithIngredientsResponse {
    private UUID id;

    private String name;

    private CocktailType type;

    private List<Ingredient> ingredients;
}
