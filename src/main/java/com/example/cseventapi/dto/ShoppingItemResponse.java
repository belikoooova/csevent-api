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
public class ShoppingItemResponse {
    @JsonProperty("product_id")
    private UUID productId;

    private String name;

    private ProductTag tag;

    private String unit;

    private Double amount;

    private Double price;

    @JsonProperty("is_purchased")
    private Boolean isPurchased;
}
