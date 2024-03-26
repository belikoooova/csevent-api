package com.example.cseventapi.service;

import com.example.cseventapi.dto.ShoppingItemInfoResponse;
import com.example.cseventapi.dto.ShoppingItemRequest;
import com.example.cseventapi.dto.ShoppingItemResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ShoppingService {
    @Transactional
    List<ShoppingItemResponse> getAllShoppingItems(UUID eventId);

    @Transactional
    void updatePurchased(UUID eventId, UUID productId);

    @Transactional
    ShoppingItemInfoResponse getInfoAboutProduct(UUID eventId, UUID productId);

    void update(UUID eventId, UUID productId, ShoppingItemRequest request);
}
