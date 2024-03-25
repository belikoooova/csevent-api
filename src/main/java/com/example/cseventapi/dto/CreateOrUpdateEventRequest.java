package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class CreateOrUpdateEventRequest {
    private static final String NAME_NOT_BLANK = "Название не может быть пустым";
    private static final String POSITIVE_AMOUNT = "Количество должно быть неотрицательным";
    private static final String ORG_ID_NOT_BLANK = "ID организации не может быть пустым";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    private String address;

    @PositiveOrZero(message = POSITIVE_AMOUNT)
    private Integer guests;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    private String color;

    @NotNull(message = ORG_ID_NOT_BLANK)
    @JsonProperty("organization_id")
    private UUID organizationId;
}
