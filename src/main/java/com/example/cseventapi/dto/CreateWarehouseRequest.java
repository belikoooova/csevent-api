package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWarehouseRequest {
    private static final String NAME_NOT_BLANK = "Название не может быть пустым";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    private String address;

    @JsonProperty("organization_id")
    private UUID organizationId;
}
