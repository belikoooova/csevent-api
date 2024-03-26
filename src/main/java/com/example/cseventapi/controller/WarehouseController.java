package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getAll(@PathVariable UUID organizationId) {
        return warehouseService.findAllByOrganisationId(organizationId);
    }

    @PostMapping
    public Warehouse create(@PathVariable UUID organizationId, @RequestBody @Valid CreateWarehouseRequest request) {
        return warehouseService.create(organizationId, request);
    }

    @GetMapping("/{warehouseId}")
    public WarehouseWithProductsResponse get(@PathVariable UUID warehouseId, @PathVariable UUID organizationId) {
        return warehouseService.getWarehouseWithProducts(warehouseId);
    }

    @GetMapping("/{warehouseId}/products/get-hints")
    public List<ShortProductResponse> getHintsForAutocompleteField(
            @PathVariable UUID organizationId,
            @PathVariable UUID warehouseId
    ) {
        return warehouseService.getProductsForAutocompleteField(organizationId, warehouseId);
    }

    @PostMapping("/{warehouseId}/products")
    public Product saveNewProduct(
            @PathVariable UUID organizationId,
            @PathVariable UUID warehouseId,
            @RequestBody @Valid CreateOrUpdateProductRequest request
    ) {
        return warehouseService.saveNewProductOnWarehouse(organizationId, warehouseId, request);
    }

    @PutMapping("/{warehouseId}/products/{productId}")
    public Product updateProduct(
            @PathVariable UUID warehouseId,
            @PathVariable UUID productId,
            @RequestBody @Valid CreateOrUpdateProductRequest request,
            @PathVariable UUID organizationId
    ) {
        return warehouseService.update(warehouseId, productId, request);
    }

    @DeleteMapping("/{warehouseId}")
    public Warehouse delete(@PathVariable UUID warehouseId, @PathVariable UUID organizationId) {
        return warehouseService.delete(warehouseId);
    }

    @DeleteMapping("/{warehouseId}/products/{productId}")
    public void deleteProduct(
            @PathVariable UUID warehouseId,
            @PathVariable UUID productId,
            @PathVariable UUID organizationId
    ) {
        warehouseService.deleteProduct(warehouseId, productId);
    }
}
