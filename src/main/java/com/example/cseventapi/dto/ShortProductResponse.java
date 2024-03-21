package com.example.cseventapi.dto;

import com.example.cseventapi.entity.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortProductResponse {
    private UUID id;
    private String name;
    private ProductTag tag;
    private String unit;
    private Double amount;
}
