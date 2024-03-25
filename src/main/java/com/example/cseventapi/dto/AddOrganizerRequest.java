package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrganizerRequest {
    private static final String ORG_ID_NOT_BLANK = "ID организатора не может быть пустым";

    @NotNull(message = ORG_ID_NOT_BLANK)
    @JsonProperty("organizer_id")
    private UUID organizerId;
}
