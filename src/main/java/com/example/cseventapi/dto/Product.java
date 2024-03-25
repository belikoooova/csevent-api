package com.example.cseventapi.dto;

import com.example.cseventapi.entity.ProductTag;
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
public class Product {
    private UUID id;

    private String name;

    private String unit;

    @JsonProperty("organization_id")
    private UUID organizationId;

    private ProductTag tag;
}
