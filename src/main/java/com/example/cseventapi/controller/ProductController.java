package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.ProductTag;
import com.example.cseventapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PatchMapping
    public List<ShortProductResponse> getAll(
            @PathVariable UUID organizationId,
            @RequestBody @Valid SearchAndFilterProductRequest request
        ) {
        return productService.getProductsWithGeneralAmount(organizationId, request);
    }

    @GetMapping("/{productId}")
    public ProductWithWarehousesResponse get(@PathVariable UUID organizationId, @PathVariable UUID productId) {
        return productService.getProductWithWarehouses(productId);
    }

    @DeleteMapping("/{productId}")
    public Product delete(@PathVariable UUID organizationId, @PathVariable UUID productId) {
        return productService.delete(productId);
    }
}
