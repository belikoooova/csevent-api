package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CocktailService {
    @Transactional
    List<CocktailWithIngredientsResponse> getAll(EventIdRequest request);

    @Transactional
    Cocktail create(CreateOrUpdateCocktailRequest request);

    @Transactional
    Cocktail update(UUID cocktailId, CreateOrUpdateCocktailRequest request);

    @Transactional
    CocktailWithIngredientsResponse get(UUID cocktailId);

    @Transactional
    Cocktail delete(UUID cocktailId);

    @Transactional
    void deleteProduct(UUID cocktailId, UUID productId);

    @Transactional
    List<ShortProductResponse> getProductsForAutocompleteField(UUID cocktailId);

    @Transactional
    Product saveNewProductInCocktail(UUID cocktailId, CreateNewProductRequest request);

    // TODO: Factory Method
    @Transactional
    Product updateProduct(UUID cocktailId, UpdateProductRequest request);
}
