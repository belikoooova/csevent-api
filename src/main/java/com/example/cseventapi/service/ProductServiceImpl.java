package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.Product;
import com.example.cseventapi.entity.ProductTag;
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
    public List<ShortProductResponse> getProductsWithGeneralAmount(UUID organizationId, SearchAndFilterProductRequest request) {
        List<Product> products = productDao.findAllByOrganizationId(organizationId);

        List<ShortProductResponse> productResponses = products.stream()
                .map(p -> ShortProductResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .unit(p.getUnit())
                        .tag(p.getTag())
                        .amount(getTotalProductAmount(p.getId()))
                        .build())
                .toList();

        return getSearchedListProduct(
                getFilteredListProduct(productResponses, request.getTags()),
                request.getSubstring()
        );
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
    public com.example.cseventapi.dto.Product getExistingOrCreateNewProduct(UUID organizationId, CreateOrUpdateProductRequest request) {
        com.example.cseventapi.dto.Product product;

        Optional<Product> productModel = productDao.findByNameAndTagAndUnitAndOrganizationId(
                request.getName(),
                request.getTag(),
                request.getUnit(),
                organizationId
        );

        if (productModel.isPresent()) {
            product = productDataModelToProductDtoMapper.map(productModel.get());
        } else {
            product = com.example.cseventapi.dto.Product.builder()
                    .organizationId(organizationId)
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

    private List<ShortProductResponse> getFilteredListProduct(List<ShortProductResponse> productResponses, List<ProductTag> tags) {
        return productResponses.stream()
                .filter(p -> tags.contains(p.getTag()))
                .toList();
    }

    private List<ShortProductResponse> getSearchedListProduct(List<ShortProductResponse> productResponses, String substring) {
        return productResponses.stream()
                .filter(p -> p.getName().toLowerCase().contains(substring.toLowerCase()))
                .toList();
    }
}
