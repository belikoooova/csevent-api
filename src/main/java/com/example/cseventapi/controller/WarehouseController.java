package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getAll(@RequestBody @Valid OrganizationIdRequest request) {
        return warehouseService.findAllByOrganisationId(request);
    }

    @PostMapping("/create")
    public Warehouse create(@RequestBody @Valid CreateWarehouseRequest request) {
        return warehouseService.create(request);
    }

    @GetMapping("/{id}")
    public WarehouseWithProductsResponse get(@PathVariable UUID id) {
        return warehouseService.getWarehouseWithProducts(id);
    }

    @DeleteMapping("/{id}")
    public Warehouse delete(@PathVariable UUID id) {
        return warehouseService.delete(id);
    }

    @DeleteMapping("/{warehouseId}/delete-product/{productId}")
    public void deleteProduct(@PathVariable UUID warehouseId, @PathVariable UUID productId) {
        warehouseService.deleteProduct(warehouseId, productId);
    }

    @GetMapping("/{id}/get-product-hints")
    public List<ShortProductResponse> getHintsForAutocompleteField(@PathVariable UUID id) {
        return warehouseService.getProductsForAutocompleteField(id);
    }

    @PostMapping("/{id}/save-product")
    public Product saveNewProduct(@PathVariable UUID id, @RequestBody @Valid CreateNewProductRequest request) {
        return warehouseService.saveNewProductOnWarehouse(id, request);
    }

    @PutMapping("/{id}/update-product")
    public Product updateProduct(@PathVariable UUID id, @RequestBody @Valid UpdateProductRequest request) {
        return warehouseService.update(id, request);
    }
}
