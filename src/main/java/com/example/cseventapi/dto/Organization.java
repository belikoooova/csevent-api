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
public class Organization {
    @NotNull
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private String nickname;

    @NotNull
    @JsonProperty("secret_code")
    private String secretCode;
}
