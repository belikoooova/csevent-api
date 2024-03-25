package com.example.cseventapi.controller;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.service.CocktailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;

    @GetMapping
    public List<CocktailWithIngredientsResponse> getAll(@RequestBody EventIdRequest request) {
        return cocktailService.getAll(request);
    }

    @GetMapping("/{id}")
    public CocktailWithIngredientsResponse get(@PathVariable UUID id) {
        return cocktailService.get(id);
    }

    @PostMapping
    public Cocktail create(@RequestBody CreateOrUpdateCocktailRequest request) {
        return cocktailService.create(request);
    }

    @PutMapping("/{id}")
    public Cocktail update(@PathVariable UUID id, @RequestBody CreateOrUpdateCocktailRequest request) {
        return cocktailService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Cocktail delete(@PathVariable UUID id) {
        return cocktailService.delete(id);
    }

    @DeleteMapping("/{cocktailId}/delete-product/{productId}")
    public void deleteProduct(@PathVariable UUID cocktailId, @PathVariable UUID productId) {
        cocktailService.deleteProduct(cocktailId, productId);
    }

    @GetMapping("/{id}/get-product-hints")
    public List<ShortProductResponse> getHintsForAutocompleteField(@PathVariable UUID id) {
        return cocktailService.getProductsForAutocompleteField(id);
    }

    @PostMapping("/{id}/save-product")
    public Product saveNewProduct(@PathVariable UUID id, @RequestBody @Valid CreateNewProductRequest request) {
        return cocktailService.saveNewProductInCocktail(id, request);
    }

    @PutMapping("/{id}/update-product")
    public Product updateProduct(@PathVariable UUID id, @RequestBody @Valid UpdateProductRequest request) {
        return cocktailService.updateProduct(id, request);
    }
}
