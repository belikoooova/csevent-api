package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.CocktailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/events/{eventId}/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;

    @GetMapping
    public List<CocktailWithIngredientsResponse> getAll(@PathVariable UUID organizationId, @PathVariable UUID eventId) {
        return cocktailService.getAll(eventId);
    }

    @GetMapping("/{cocktailId}")
    public CocktailWithIngredientsResponse get(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID cocktailId
    ) {
        return cocktailService.get(cocktailId);
    }

    @PostMapping
    public Cocktail create(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @RequestBody @Valid CreateOrUpdateCocktailRequest request
    ) {
        return cocktailService.create(eventId, request);
    }

    @PutMapping("/{cocktailId}")
    public Cocktail update(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID cocktailId,
            @RequestBody CreateOrUpdateCocktailRequest request
    ) {
        return cocktailService.update(cocktailId, request);
    }

    @DeleteMapping("/{cocktailId}")
    public Cocktail delete(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID cocktailId
    ) {
        return cocktailService.delete(cocktailId);
    }

    @DeleteMapping("/{cocktailId}/products/{productId}")
    public void deleteProduct(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID cocktailId,
            @PathVariable UUID productId
    ) {
        cocktailService.deleteProduct(cocktailId, productId);
    }

    @GetMapping("/{cocktailId}/products/get-hints")
    public List<ShortProductResponse> getHintsForAutocompleteField(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID cocktailId
    ) {
        return cocktailService.getProductsForAutocompleteField(organizationId, cocktailId);
    }

    @PostMapping("/{cocktailId}/products")
    public Product saveNewProduct(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID cocktailId,
            @RequestBody @Valid CreateOrUpdateProductRequest request
    ) {
        return cocktailService.saveNewProductInCocktail(organizationId, cocktailId, eventId, request);
    }

    @PutMapping("/{cocktailId}/products/{productId}")
    public Product updateProduct(
            @PathVariable UUID organizationId,
            @PathVariable UUID eventId,
            @PathVariable UUID cocktailId,
            @PathVariable UUID productId,
            @RequestBody @Valid CreateOrUpdateProductRequest request
    ) {
        return cocktailService.updateProduct(cocktailId, productId, request);
    }
}
