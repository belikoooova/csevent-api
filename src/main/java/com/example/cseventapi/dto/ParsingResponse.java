package com.example.cseventapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsingResponse {
    private String shop;
    private String price;
    private String url;
    private boolean founded;
}
