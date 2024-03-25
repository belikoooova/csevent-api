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

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private static final String NAME_NOT_BLANK = "Название не может быть пустым";
    private static final String UNIT_NOT_BLANK = "Единица измерения не может быть пустой";
    private static final String AMOUNT_NOT_BLANK = "Количество не может быть пустым";
    private static final String TAG_NOT_BLANK = "Тэг не может быть пустым";
    private static final String PROD_ID_NOT_BLANK = "ID организации не может быть пустым";
    private static final String POSITIVE_AMOUNT = "Количество должно быть неотрицательным";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = UNIT_NOT_BLANK)
    private String unit;

    @NotNull(message = AMOUNT_NOT_BLANK)
    @PositiveOrZero(message = POSITIVE_AMOUNT)
    private Double amount;

    @NotNull(message = TAG_NOT_BLANK)
    private ProductTag tag;

    @NotNull(message = PROD_ID_NOT_BLANK)
    @JsonProperty("product_id")
    private UUID productId;
}
