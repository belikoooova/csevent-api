package com.example.cseventapi.dto;

import com.example.cseventapi.entity.CocktailType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateCocktailRequest {
    private static final String NAME_NOT_BLANK = "Название не может быть пустым";
    private static final String TYPE_NOT_NULL = "Тип пуст или некорректен";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotNull(message = TYPE_NOT_NULL)
    private CocktailType type;
}
