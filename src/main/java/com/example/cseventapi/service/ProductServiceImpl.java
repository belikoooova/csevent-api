package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.Product;
import com.example.cseventapi.repository.ProductDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductDao productDao;
    private final EntityManager entityManager;

    @Transactional
    public List<ShortProductResponse> getProductsWithGeneralAmount(UUID organizationId) {
        List<Product> products = productDao.findAllByOrganizationId(organizationId);

        return products.stream()
                .map(p -> ShortProductResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .unit(p.getUnit())
                        .tag(p.getTag())
                        .amount(getTotalProductAmount(organizationId, p.getId()))
                        .build())
                .toList();
    }

    @Transactional
    public List<ShortProductResponse> getFilteredListProduct(UUID organizationId, FilterProductsRequest request) {
        return getProductsWithGeneralAmount(organizationId).stream()
                .filter(p -> request.getTags().contains(p.getTag()))
                .toList();
    }

    @Transactional
    public List<ShortProductResponse> getSearchedListProduct(UUID organizationId, SearchProductRequest request) {
        return getProductsWithGeneralAmount(organizationId).stream()
                .filter(p -> p.getName().toLowerCase().contains(request.getSubstring().toLowerCase()))
                .toList();
    }

    @Transactional
    public ProductWithWarehousesResponse getProductWithWarehouses(UUID productId) {
        Product product = productDao.findById(productId).get();
        return ProductWithWarehousesResponse.builder()
                .id(productId)
                .name(product.getName())
                .unit(product.getUnit())
                .warehouses(getWarehousesResponseWithAmount(productId))
                .build();
    }

    private Double getTotalProductAmount(UUID organizationId, UUID productId) {
        Number result = (Number) entityManager.createNativeQuery(
                "select sum(amount) from products p " +
                         "join product_warehouse pw on p.id = pw.product_id " +
                         "where p.organization_id = :organizationId " +
                         "and p.id = :productId"
                ).setParameter("organizationId", organizationId)
                .setParameter("productId", productId)
                .getSingleResult();
        return result != null ? result.doubleValue() : 0.0;
    }

    private List<ShortWarehouseResponseWithAmount> getWarehousesResponseWithAmount(UUID productId) {
        List<Object[]> results = entityManager.createNativeQuery(
                "select w.id, w.name, w.address, pw.amount from warehouses w " +
                         "join product_warehouse pw on w.id = pw.warehouse_id " +
                         "where pw.product_id = :productId"
                ).setParameter("productId", productId)
                .getResultList();

        return results.stream()
                .map(o -> ShortWarehouseResponseWithAmount.builder()
                        .id((UUID) o[0])
                        .name((String) o[1])
                        .address((String) o[2])
                        .amount((Double) o[3])
                        .build()
                ).toList();
    }
}
