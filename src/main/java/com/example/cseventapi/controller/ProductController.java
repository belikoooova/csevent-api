package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ShortProductResponse> getAll(@RequestBody @Valid OrganizationIdRequest request) {
        return productService.getProductsWithGeneralAmount(request);
    }

    @GetMapping("/search")
    public List<ShortProductResponse> getAllSearched(@RequestBody @Valid SearchProductRequest request) {
        return productService.getSearchedListProduct(request);
    }

    @GetMapping("/filter")
    public List<ShortProductResponse> getAllFiltered(@RequestBody @Valid FilterProductsRequest request) {
        return productService.getFilteredListProduct(request);
    }

    @GetMapping("/{id}")
    public ProductWithWarehousesResponse get(@PathVariable UUID id) {
        return productService.getProductWithWarehouses(id);
    }

    @DeleteMapping("/{id}")
    public Product delete(@PathVariable UUID id) {
        return productService.delete(id);
    }
}
