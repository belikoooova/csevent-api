package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.ProductService;
import com.example.cseventapi.service.ShoppingService;
import com.example.cseventapi.service.parsing.ParsingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/events/{eventId}/shopping")
@RequiredArgsConstructor
public class ShoppingController {
    private final ShoppingService shoppingService;
    private final ProductService productService;
    private final ParsingService parsingService;

    @GetMapping
    public List<ShoppingItemResponse> getAll(@PathVariable UUID organizationId, @PathVariable UUID eventId) {
        return shoppingService.getAllShoppingItems(eventId);
    }

    @GetMapping("/{productId}")
    public ShoppingItemInfoResponse get(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID productId
    ) {
        return shoppingService.getInfoAboutProduct(eventId, productId);
    }

    @PatchMapping("/{productId}")
    public void updateIsPurchased(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID productId
    ) {
        shoppingService.updatePurchased(eventId, productId);
    }

    @GetMapping("/{productId}/warehouses")
    public ProductWithWarehousesResponse getWithWarehouses(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID productId
    ) {
        return productService.getProductWithWarehouses(productId);
    }

    @PutMapping("/{productId}")
    public void update(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID productId,
            @RequestBody @Valid ShoppingItemRequest request
    ) {
        shoppingService.update(eventId, productId, request);
    }

    @GetMapping("/{productId}/shops")
    public List<ParsingResponse> getWithShops(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID productId
    ) {
        return parsingService.getShops(productId);
    }
}
