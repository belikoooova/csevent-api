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
public class ShoppingItemInfoResponse {
    @JsonProperty("product_id")
    private UUID productId;

    private String name;

    private String unit;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("to_buy_amount")
    private Double toBuyAmount;

    private Double price;

}
