package com.example.cseventapi.service;

import com.example.cseventapi.dto.*;
import com.example.cseventapi.entity.ProductTag;
import com.example.cseventapi.exception.ProductWithSameNameAlreadyInCocktailException;
import com.example.cseventapi.mapper.CocktailDataModelToCocktailDtoMapper;
import com.example.cseventapi.mapper.CocktailDtoToCocktailDataModelMapper;
import com.example.cseventapi.mapper.ProductDataModelToProductDtoMapper;
import com.example.cseventapi.mapper.ProductDtoToProductDataModelMapper;
import com.example.cseventapi.repository.CocktailDao;
import com.example.cseventapi.repository.EventDao;
import com.example.cseventapi.repository.ProductDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {
    private final CocktailDao cocktailDao;
    private final EventDao eventDao;
    private final ProductDao productDao;
    private final ProductService productService;
    private final EntityManager entityManager;
    private final CocktailDataModelToCocktailDtoMapper cocktailDataModelToCocktailDtoMapper;
    private final CocktailDtoToCocktailDataModelMapper cocktailDtoToCocktailDataModelMapper;
    private final ProductDataModelToProductDtoMapper productDataModelToProductDtoMapper;
    private final ProductDtoToProductDataModelMapper productDtoToProductDataModelMapper;

    @Override
    @Transactional
    public List<CocktailWithIngredientsResponse> getAll(EventIdRequest request) {
        List<com.example.cseventapi.entity.Cocktail> cocktails = cocktailDao.findAllByEventId(request.getEventId());

        List<CocktailWithIngredientsResponse> responses = new ArrayList<>();

        cocktails.forEach(
                c -> responses.add(get(c.getId()))
        );

        return responses;
    }

    @Override
    @Transactional
    public Cocktail create(CreateOrUpdateCocktailRequest request) {
        Cocktail cocktail = Cocktail.builder()
                .eventId(request.getEventId())
                .name(request.getName())
                .type(request.getType())
                .build();

        return cocktailDataModelToCocktailDtoMapper.map(
                cocktailDao.save(cocktailDtoToCocktailDataModelMapper.map(cocktail))
        );
    }

    @Override
    @Transactional
    public Cocktail update(UUID cocktailId, CreateOrUpdateCocktailRequest request) {
        Cocktail cocktail = cocktailDataModelToCocktailDtoMapper.map(cocktailDao.findById(cocktailId).get());

        cocktail.setType(request.getType());
        cocktail.setName(request.getName());

        return cocktailDataModelToCocktailDtoMapper.map(
                cocktailDao.save(cocktailDtoToCocktailDataModelMapper.map(cocktail))
        );
    }

    @Override
    @Transactional
    public CocktailWithIngredientsResponse get(UUID cocktailId) {
        Cocktail cocktail = cocktailDataModelToCocktailDtoMapper.map(cocktailDao.findById(cocktailId).get());
        CocktailWithIngredientsResponse response = CocktailWithIngredientsResponse.builder()
                .id(cocktail.getId())
                .name(cocktail.getName())
                .type(cocktail.getType())
                .build();

        List<Object[]> objects = entityManager.createNativeQuery(
                "select p.id, p.name, p.tag, cp.amount from cocktails c " +
                        "join cocktail_product cp on c.id = cp.cocktail_id " +
                        "join products p on cp.product_id = p.id " +
                        "where c.id = :cocktailId"
                ).setParameter("cocktailId", cocktailId)
                .getResultList();

        List<Ingredient> ingredients = objects.stream()
                .map(o -> Ingredient.builder()
                        .productId((UUID) o[0])
                        .name((String) o[1])
                        .tag(ProductTag.valueOf((String) o[2]))
                        .amount((Double) o[3])
                        .build())
                .toList();

        response.setIngredients(ingredients);

        return response;
    }

    @Override
    @Transactional
    public Cocktail delete(UUID cocktailId) {
        Cocktail cocktail = cocktailDataModelToCocktailDtoMapper.map(cocktailDao.findById(cocktailId).get());
        cocktailDao.deleteById(cocktailId);
        return cocktail;
    }

    @Override
    @Transactional
    public void deleteProduct(UUID cocktailId, UUID productId) {
        entityManager.createNativeQuery(
                        "delete from cocktail_product cp " +
                                "where cp.cocktail_id = :cocktailId and cp.product_id = :productId"
                ).setParameter("cocktailId", cocktailId)
                .setParameter("productId", productId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public List<ShortProductResponse> getProductsForAutocompleteField(UUID cocktailId) {
        return getProductResponsesForAutocompleteField(
                eventDao.findById(cocktailDao.findById(cocktailId).get().getEventId()).get().getOrganizationId(),
                cocktailId
        );
    }

    @Override
    @Transactional
    public Product saveNewProductInCocktail(UUID cocktailId, CreateNewProductRequest request) {
        int countWithSameName = entityManager.createNativeQuery(
                        "select count(*) from products p " +
                                "join cocktail_product cp on p.id = cp.product_id " +
                                "where cp.cocktail_id = :cocktailId and p.name = :name"
                ).setParameter("name", request.getName())
                .setParameter("cocktailId", cocktailId)
                .getFirstResult();

        if (countWithSameName != 0) {
            throw new ProductWithSameNameAlreadyInCocktailException();
        }

        Product savedProduct = productService.getExistingOrCreateNewProduct(request);

        addProductToCocktail(savedProduct.getId(), cocktailId, request.getAmount());

        return savedProduct;
    }

    // TODO: Factory Method
    @Override
    @Transactional
    public Product updateProduct(UUID cocktailId, UpdateProductRequest request) {
        Product product = productDataModelToProductDtoMapper.map(productDao.findById(request.getProductId()).get());

        product.setName(request.getName());
        product.setUnit(request.getUnit());
        product.setTag(request.getTag());

        entityManager.createNativeQuery("update cocktail_product " +
                        "set amount = :amount " +
                        "where cocktail_id = :cocktailId and product_id = :productId")
                .setParameter("cocktailId", cocktailId)
                .setParameter("productId", request.getProductId())
                .setParameter("amount", request.getAmount())
                .executeUpdate();

        return productDataModelToProductDtoMapper.map(
                productDao.save(productDtoToProductDataModelMapper.map(product))
        );
    }

    private List<ShortProductResponse> getProductResponsesForAutocompleteField(UUID organizationId, UUID cocktailId) {
        List<Object[]> results = entityManager.createNativeQuery(
                        "select p.id, p.name, p.unit, p.tag from products p " +
                                "where p.organization_id = :organizationId and p.id not in (" +
                                    "select p1.id from products p1 " +
                                    "join cocktail_product cp1 on p1.id = cp1.product_id " +
                                    "where cp1.cocktail_id = :cocktailId" +
                                ")"
                ).setParameter("organizationId", organizationId)
                .setParameter("cocktailId", cocktailId)
                .getResultList();

        return mapToListShortProductResponse(results);
    }

    private List<ShortProductResponse> mapToListShortProductResponse(List<Object[]> objects) {
        return objects.stream()
                .map(o -> ShortProductResponse.builder()
                        .id((UUID) o[0])
                        .name((String) o[1])
                        .unit((String) o[2])
                        .tag(ProductTag.valueOf((String) o[3]))
                        .amount(o.length == 5 ? (Double)o[4] : 0)
                        .build()
                ).toList();
    }

    private void addProductToCocktail(UUID productId, UUID cocktailId, double amount) {
        entityManager.createNativeQuery(
                        "insert into cocktail_product (product_id, cocktail_id, amount) " +
                                "values (:productId, :cocktailId, :amount)"
                ).setParameter("productId", productId)
                .setParameter("cocktailId", cocktailId)
                .setParameter("amount", amount)
                .executeUpdate();
    }
}
