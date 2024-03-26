package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CocktailService {
    @Transactional
    List<CocktailWithIngredientsResponse> getAll(UUID eventId);

    @Transactional
    Cocktail create(UUID eventId, CreateOrUpdateCocktailRequest request);

    @Transactional
    Cocktail update(UUID cocktailId, CreateOrUpdateCocktailRequest request);

    @Transactional
    CocktailWithIngredientsResponse get(UUID cocktailId);

    @Transactional
    Cocktail delete(UUID cocktailId);

    @Transactional
    void deleteProduct(UUID cocktailId, UUID productId);

    @Transactional
    List<ShortProductResponse> getProductsForAutocompleteField(UUID organizationId, UUID cocktailId);

    @Transactional
    Product saveNewProductInCocktail(UUID organizationId, UUID cocktailId, UUID eventId, CreateOrUpdateProductRequest request);

    @Transactional
    Product updateProduct(UUID cocktailId, UUID productId, CreateOrUpdateProductRequest request);
}
