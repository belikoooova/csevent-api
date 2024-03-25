package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.ProductTag;
import com.example.cseventapi.exception.ProductWithSameNameAlreadyInWarehouseException;
import com.example.cseventapi.mapper.ProductDataModelToProductDtoMapper;
import com.example.cseventapi.mapper.ProductDtoToProductDataModelMapper;
import com.example.cseventapi.mapper.WarehouseDataModelToWarehouseDtoMapper;
import com.example.cseventapi.mapper.WarehouseDtoToWarehouseDataModelMapper;
import com.example.cseventapi.repository.ProductDao;
import com.example.cseventapi.repository.WarehouseDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseDao warehouseDao;
    private final ProductDao productDao;
    private final ProductService productService;
    private final WarehouseDataModelToWarehouseDtoMapper warehouseDataModelToWarehouseDtoMapper;
    private final WarehouseDtoToWarehouseDataModelMapper warehouseDtoToWarehouseDataModelMapper;
    private final ProductDataModelToProductDtoMapper productDataModelToProductDtoMapper;
    private final ProductDtoToProductDataModelMapper productDtoToProductDataModelMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public List<Warehouse> findAllByOrganisationId(OrganizationIdRequest request) {
        return warehouseDao.findAllByOrganizationId(request.getOrganizationId()).stream()
                .map(warehouseDataModelToWarehouseDtoMapper::map)
                .toList();
    }

    @Override
    @Transactional
    public Warehouse create(CreateWarehouseRequest request) {
        Warehouse warehouse = Warehouse.builder()
                .address(request.getAddress())
                .name(request.getName())
                .organizationId(request.getOrganizationId())
                .build();

        return warehouseDataModelToWarehouseDtoMapper.map(
                warehouseDao.save(
                        warehouseDtoToWarehouseDataModelMapper.map(warehouse)
                )
        );
    }

    @Override
    @Transactional
    public WarehouseWithProductsResponse getWarehouseWithProducts(UUID warehouseId) {
        Warehouse warehouse = warehouseDataModelToWarehouseDtoMapper.map(warehouseDao.findById(warehouseId).get());

        return WarehouseWithProductsResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .products(getProductResponse(warehouse.getId()))
                .build();
    }

    @Override
    @Transactional
    public List<ShortProductResponse> getProductsForAutocompleteField(UUID warehouseId) {
        return getProductResponsesForAutocompleteField(
                warehouseDao.findById(warehouseId).get().getOrganizationId(),
                warehouseId
        );
    }

    @Override
    @Transactional
    public Product saveNewProductOnWarehouse(UUID warehouseId, CreateNewProductRequest request) {
        int countWithSameName = entityManager.createNativeQuery(
                        "select count(*) from products p " +
                                "join product_warehouse pw on p.id = pw.product_id " +
                                "where pw.warehouse_id = :warehouseId and p.name = :name"
                ).setParameter("name", request.getName())
                .setParameter("warehouseId", warehouseId)
                .getFirstResult();

        if (countWithSameName != 0) {
            throw new ProductWithSameNameAlreadyInWarehouseException();
        }

        Product savedProduct = productService.getExistingOrCreateNewProduct(request);

        addProductToWarehouse(savedProduct.getId(), warehouseId, request.getAmount());

        return savedProduct;
    }

    @Override
    @Transactional
    public Product update(UUID warehouseId, UpdateProductRequest request) {
        Product product = productDataModelToProductDtoMapper.map(productDao.findById(request.getProductId()).get());

        product.setName(request.getName());
        product.setUnit(request.getUnit());
        product.setTag(request.getTag());

        entityManager.createNativeQuery("update product_warehouse " +
                "set amount = :amount " +
                "where warehouse_id = :warehouseId and product_id = :productId")
                .setParameter("warehouseId", warehouseId)
                .setParameter("productId", request.getProductId())
                .setParameter("amount", request.getAmount())
                .executeUpdate();

        return productDataModelToProductDtoMapper.map(
                productDao.save(productDtoToProductDataModelMapper.map(product))
        );
    }

    @Override
    @Transactional
    public Warehouse delete(UUID id) {
        Warehouse toDelete = warehouseDataModelToWarehouseDtoMapper.map(warehouseDao.findById(id).get());
        warehouseDao.deleteById(id);
        return toDelete;
    }

    @Override
    @Transactional
    public void deleteProduct(UUID warehouseId, UUID productId) {
        entityManager.createNativeQuery(
                "delete from product_warehouse pw " +
                        "where pw.warehouse_id = :warehouseId and pw.product_id = :productId"
                ).setParameter("warehouseId", warehouseId)
                .setParameter("productId", productId)
                .executeUpdate();
    }

    private List<ShortProductResponse> getProductResponse(UUID warehouseId) {
        List<Object[]> results = entityManager.createNativeQuery(
                        "select p.id, p.name, p.unit, p.tag, pw.amount from products p " +
                                "join product_warehouse pw on p.id = pw.product_id " +
                                "where pw.warehouse_id = :warehouseId"
                ).setParameter("warehouseId", warehouseId)
                .getResultList();

        return mapToListShortProductResponse(results);
    }

    private List<ShortProductResponse> getProductResponsesForAutocompleteField(UUID organizationId, UUID warehouseId) {
        List<Object[]> results = entityManager.createNativeQuery(
                        "select p.id, p.name, p.unit, p.tag from products p " +
                                "join product_warehouse pw on p.id = pw.product_id " +
                                "where p.organization_id = :organizationId and p.id not in (" +
                                    "select p1.id from products p1 " +
                                    "join product_warehouse pw1 on p1.id = pw1.product_id " +
                                    "where pw1.warehouse_id = :warehouseId" +
                                ")"
                ).setParameter("organizationId", organizationId)
                .setParameter("warehouseId", warehouseId)
                .getResultList();

        return mapToListShortProductResponse(results);
    }

    private void addProductToWarehouse(UUID productId, UUID warehouseId, double amount) {
        entityManager.createNativeQuery(
                "insert into product_warehouse (product_id, warehouse_id, amount) " +
                        "values (:productId, :warehouseId, :amount)"
                ).setParameter("productId", productId)
                .setParameter("warehouseId", warehouseId)
                .setParameter("amount", amount)
                .executeUpdate();
    }

    private List<ShortProductResponse> mapToListShortProductResponse(List<Object[]> objects) {
        return objects.stream()
                .map(o -> ShortProductResponse.builder()
                        .id((UUID) o[0])
                        .name((String) o[1])
                        .unit((String) o[2])
                        .tag(ProductTag.valueOf((String) o[3]))
                        .amount(o.length == 5 ? (Double)o[4] : 0)
                        .build()
                ).toList();
    }
}
