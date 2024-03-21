package com.example.cseventapi.repository;

import com.example.cseventapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductDao extends JpaRepository<Product, UUID> {
    List<Product> findAllByOrganizationId(UUID organizationId);
}
