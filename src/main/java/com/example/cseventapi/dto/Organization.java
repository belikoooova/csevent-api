package com.example.cseventapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private UUID id;

    private String title;

    private String nickname;

    @JsonProperty("secret_code")
    private String secretCode;
}
