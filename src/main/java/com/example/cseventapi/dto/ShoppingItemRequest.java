package com.example.cseventapi.dto;

import com.example.cseventapi.entity.ProductTag;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingItemRequest {
    private static final String AMOUNT_NOT_BLANK = "Количество не может быть пустым";
    private static final String POSITIVE_AMOUNT = "Количество должно быть неотрицательным";
    private static final String PRICE_NOT_BLANK = "Цена не может быть пустым";
    private static final String PRICE_AMOUNT = "Цена должна быть неотрицательным";

    @NotNull(message = PRICE_NOT_BLANK)
    @PositiveOrZero(message = POSITIVE_AMOUNT)
    private Double price;

    @NotNull(message = AMOUNT_NOT_BLANK)
    @PositiveOrZero(message = POSITIVE_AMOUNT)
    @JsonProperty("to_buy_amount")
    private Double toBuyAmount;
}
