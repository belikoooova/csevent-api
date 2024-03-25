package com.example.cseventapi.repository;

import com.example.cseventapi.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WarehouseDao extends JpaRepository<Warehouse, UUID> {
    List<Warehouse> findAllByOrganizationId(UUID organisationId);
}
