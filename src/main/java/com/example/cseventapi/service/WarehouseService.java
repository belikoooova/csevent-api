package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WarehouseService {
    @Transactional
    List<Warehouse> findAllByOrganisationId(UUID organizationId);

    @Transactional
    Warehouse create(UUID organizationId, CreateWarehouseRequest request);

    @Transactional
    WarehouseWithProductsResponse getWarehouseWithProducts(UUID warehouseId);

    @Transactional
    List<ShortProductResponse> getProductsForAutocompleteField(UUID organizationId, UUID warehouseId);

    @Transactional
    Product saveNewProductOnWarehouse(UUID organizationId, UUID warehouseId, CreateOrUpdateProductRequest request);

    @Transactional
    Product update(UUID warehouseId, UUID productId, CreateOrUpdateProductRequest request);

    @Transactional
    Warehouse delete(UUID id);

    @Transactional
    void deleteProduct(UUID warehouseId, UUID productId);
}
