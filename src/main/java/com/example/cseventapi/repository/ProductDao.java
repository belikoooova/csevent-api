package com.example.cseventapi.repository;

import com.example.cseventapi.entity.Product;
import com.example.cseventapi.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductDao extends JpaRepository<Product, UUID> {
    List<Product> findAllByOrganizationId(UUID organizationId);

    Optional<Product> findByNameAndTagAndUnitAndOrganizationId(String name, ProductTag tag, String unit, UUID organizationId);
}
