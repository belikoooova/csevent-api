package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WarehouseService {
    @Transactional
    List<Warehouse> findAllByOrganisationId(OrganizationIdRequest request);

    @Transactional
    Warehouse create(CreateWarehouseRequest request);

    @Transactional
    WarehouseWithProductsResponse getWarehouseWithProducts(UUID warehouseId);

    @Transactional
    List<ShortProductResponse> getProductsForAutocompleteField(UUID warehouseId);

    @Transactional
    Product saveNewProductOnWarehouse(UUID warehouseId, CreateNewProductOnWarehouseRequest request);

    @Transactional
    Product update(UUID warehouseId, UpdateProductRequest request);

    @Transactional
    Warehouse delete(UUID id);

    @Transactional
    void deleteProduct(UUID warehouseId, UUID productId);
}
