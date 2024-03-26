package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    @Transactional
    List<ShortProductResponse> getProductsWithGeneralAmount(OrganizationIdRequest request);

    @Transactional
    List<ShortProductResponse> getFilteredListProduct(FilterProductsRequest request);

    @Transactional
    List<ShortProductResponse> getSearchedListProduct(SearchProductRequest request);

    @Transactional
    ProductWithWarehousesResponse getProductWithWarehouses(UUID productId);

    @Transactional
    Product delete(UUID id);

    @Transactional
    Product save(CreateNewProductRequest request);

    @Transactional
    Product getExistingOrCreateNewProduct(CreateNewProductRequest request);

    @Transactional
    Double getTotalProductAmount(UUID productId);
}
