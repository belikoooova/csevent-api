package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.Product;
import com.example.cseventapi.mapper.ProductDataModelToProductDtoMapper;
import com.example.cseventapi.mapper.ProductDtoToProductDataModelMapper;
import com.example.cseventapi.repository.ProductDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final EntityManager entityManager;
    private final ProductDataModelToProductDtoMapper productDataModelToProductDtoMapper;
    private final ProductDtoToProductDataModelMapper productDtoToProductDataModelMapper;

    @Override
    @Transactional
    public List<ShortProductResponse> getProductsWithGeneralAmount(OrganizationIdRequest request) {
        List<Product> products = productDao.findAllByOrganizationId(request.getOrganizationId());

        return products.stream()
                .map(p -> ShortProductResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .unit(p.getUnit())
                        .tag(p.getTag())
                        .amount(getTotalProductAmount(p.getId()))
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public List<ShortProductResponse> getFilteredListProduct(FilterProductsRequest request) {
        OrganizationIdRequest organizationIdRequest = OrganizationIdRequest.builder()
                .organizationId(request.getOrganizationId())
                .build();

        if (request.getTags().isEmpty()) {
            return getProductsWithGeneralAmount(organizationIdRequest);
        }

        return getProductsWithGeneralAmount(organizationIdRequest).stream()
                .filter(p -> request.getTags().contains(p.getTag()))
                .toList();
    }

    @Override
    @Transactional
    public List<ShortProductResponse> getSearchedListProduct(SearchProductRequest request) {
        OrganizationIdRequest organizationIdRequest = OrganizationIdRequest.builder()
                .organizationId(request.getOrganizationId())
                .build();

        if (request.getSubstring().isEmpty()) {
            return getProductsWithGeneralAmount(organizationIdRequest);
        }

        return getProductsWithGeneralAmount(organizationIdRequest).stream()
                .filter(p -> p.getName().toLowerCase().contains(request.getSubstring().toLowerCase()))
                .toList();
    }

    @Override
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

    @Override
    @Transactional
    public com.example.cseventapi.dto.Product delete(UUID id) {
        Product product = productDao.findById(id).get();
        productDao.deleteById(id);
        return productDataModelToProductDtoMapper.map(product);
    }

    @Override
    @Transactional
    public com.example.cseventapi.dto.Product save(CreateNewProductRequest request) {
        com.example.cseventapi.dto.Product product = com.example.cseventapi.dto.Product.builder()
                .organizationId(request.getOrganizationId())
                .unit(request.getUnit())
                .name(request.getName())
                .tag(request.getTag())
                .build();

        return productDataModelToProductDtoMapper.map(
                productDao.save(productDtoToProductDataModelMapper.map(product))
        );
    }

    @Override
    @Transactional
    public com.example.cseventapi.dto.Product getExistingOrCreateNewProduct(CreateNewProductRequest request) {
        com.example.cseventapi.dto.Product product;

        Optional<Product> productModel = productDao.findByNameAndTagAndUnitAndOrganizationId(
                request.getName(),
                request.getTag(),
                request.getUnit(),
                request.getOrganizationId()
        );

        if (productModel.isPresent()) {
            product = productDataModelToProductDtoMapper.map(productModel.get());
        } else {
            product = com.example.cseventapi.dto.Product.builder()
                    .organizationId(request.getOrganizationId())
                    .unit(request.getUnit())
                    .name(request.getName())
                    .tag(request.getTag())
                    .build();
        }

        return productDataModelToProductDtoMapper.map(
                productDao.save(productDtoToProductDataModelMapper.map(product))
        );
    }

    @Override
    @Transactional
    public Double getTotalProductAmount(UUID productId) {
        Number result = (Number) entityManager.createNativeQuery(
                        "select sum(amount) from products p " +
                                "join product_warehouse pw on p.id = pw.product_id " +
                                "and p.id = :productId"
                ).setParameter("productId", productId)
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
