package com.example.cseventapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDataRequest {
    private static final String NAME_NOT_BLANK = "Имя пользователя не может быть пустым";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    private String color;
}
