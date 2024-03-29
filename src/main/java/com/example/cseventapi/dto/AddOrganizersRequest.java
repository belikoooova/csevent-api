package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
public class AddOrganizersRequest {
    private static final String LIST_NOT_NULL = "Список организаций не распознан";

    @NotNull(message = LIST_NOT_NULL)
    @JsonProperty("organizer_ids")
    private List<UUID> organizerIds;
}
