package com.example.cseventapi.dto;

import com.example.cseventapi.entity.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterProductsRequest {
    private List<ProductTag> tags;
}
