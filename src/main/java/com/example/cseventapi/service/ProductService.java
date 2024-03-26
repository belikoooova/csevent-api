package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.ProductTag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    @Transactional
    List<ShortProductResponse> getProductsWithGeneralAmount(UUID organizationId);

    @Transactional
    List<ShortProductResponse> getFilteredListProduct(UUID organizationId, List<ProductTag> tags);

    @Transactional
    List<ShortProductResponse> getSearchedListProduct(UUID organizationId, String substring);

    @Transactional
    ProductWithWarehousesResponse getProductWithWarehouses(UUID productId);

    @Transactional
    Product delete(UUID id);

    @Transactional
    Product getExistingOrCreateNewProduct(UUID organizationId, CreateOrUpdateProductRequest request);

    @Transactional
    Double getTotalProductAmount(UUID productId);
}
