package com.example.cseventapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortWarehouseResponseWithAmount {
    private UUID id;

    private String name;

    private String address;

    private Double amount;
}
