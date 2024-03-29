package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateEventRequest {
    private static final String NAME_NOT_BLANK = "Название не может быть пустым";
    private static final String POSITIVE_AMOUNT = "Количество должно быть неотрицательным";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    private String theme;

    private String address;

    @PositiveOrZero(message = POSITIVE_AMOUNT)
    private Integer guests;

    @JsonProperty("date_time")
    private String dateTime;

    private String color;
}
