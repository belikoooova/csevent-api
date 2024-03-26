package com.example.cseventapi.dto;

import com.example.cseventapi.entity.ProductTag;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateProductRequest {
    private static final String NAME_NOT_BLANK = "Название не может быть пустым";
    private static final String UNIT_NOT_BLANK = "Единица измерения не может быть пустой";
    private static final String AMOUNT_NOT_BLANK = "Количество не может быть пустым";
    private static final String TAG_NOT_BLANK = "Тэг не может быть пустым";
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
}
