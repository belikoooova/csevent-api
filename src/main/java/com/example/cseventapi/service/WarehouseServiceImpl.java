package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.ProductTag;
import com.example.cseventapi.mapper.WarehouseDataModelToWarehouseDtoMapper;
import com.example.cseventapi.mapper.WarehouseDtoToWarehouseDataModelMapper;
import com.example.cseventapi.repository.WarehouseDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl {
    private final WarehouseDao warehouseDao;
    private final WarehouseDataModelToWarehouseDtoMapper warehouseDataModelToWarehouseDtoMapper;
    private final WarehouseDtoToWarehouseDataModelMapper warehouseDtoToWarehouseDataModelMapper;
    private final EntityManager entityManager;

    @Transactional
    public List<Warehouse> findAllByOrganisationId(UUID organisationId) {
        return warehouseDao.findAllByOrganizationId(organisationId).stream()
                .map(warehouseDataModelToWarehouseDtoMapper::map)
                .toList();
    }

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

    @Transactional
    public WarehouseWithProductsResponse getWarehouseWithProducts(UUID warehouseId) {
        Warehouse warehouse = warehouseDataModelToWarehouseDtoMapper.map(warehouseDao.findById(warehouseId).get());

        return WarehouseWithProductsResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .products(getProductResponse(warehouse.getId()))
                .build();
    }

    private List<ShortProductResponse> getProductResponse(UUID warehouseId) {
        List<Object[]> results = entityManager.createNativeQuery(
                        "select p.id, p.name, p.unit, p.tag, pw.amount from products p " +
                                "join product_warehouse pw on p.id = pw.product_id " +
                                "where pw.warehouse_id = :warehouseId"
                ).setParameter("warehouseId", warehouseId)
                .getResultList();

        return results.stream()
                .map(o -> ShortProductResponse.builder()
                        .id((UUID) o[0])
                        .name((String) o[1])
                        .unit((String) o[2])
                        .tag((ProductTag) o[3])
                        .amount((Double) o[4])
                        .build()
                ).toList();
    }
}
