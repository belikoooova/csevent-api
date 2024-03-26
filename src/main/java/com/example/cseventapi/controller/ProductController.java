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

    @GetMapping
    public List<ShortProductResponse> getAll(@PathVariable UUID organizationId) {
        return productService.getProductsWithGeneralAmount(organizationId);
    }

    @GetMapping("/search")
    public List<ShortProductResponse> getAllSearched(
            @PathVariable UUID organizationId,
            @RequestBody SearchRequest request
    ) {
        return productService.getSearchedListProduct(organizationId, request);
    }

    @GetMapping("/filter")
    public List<ShortProductResponse> getAllFiltered(
            @PathVariable UUID organizationId,
            @RequestBody @Valid List<ProductTag> tags
    ) {
        return productService.getFilteredListProduct(organizationId, tags);
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
