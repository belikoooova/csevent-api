package com.example.cseventapi.service;

import com.example.cseventapi.dto.ShoppingItemInfoResponse;
import com.example.cseventapi.dto.ShoppingItemResponse;
import com.example.cseventapi.entity.Product;
import com.example.cseventapi.repository.EventDao;
import com.example.cseventapi.repository.ProductDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingServiceImpl implements ShoppingService {
    private final EntityManager entityManager;
    private final EventDao eventDao;
    private final ProductDao productDao;
    private final ProductService productService;

    @Override
    @Transactional
    public List<ShoppingItemResponse> getAllShoppingItems(UUID eventId) {
        List<Object[]> productIds = entityManager.createNativeQuery(
                        "select ep.product_id, ep.price, ep.is_purchased, ep.to_buy_amount " +
                                "from event_product ep " +
                                "where ep.event_id = :eventId"
                        ).setParameter("eventId", eventId)
                        .getResultList();

        Integer guestsCount = eventDao.findById(eventId).get().getGuests();

        return productIds.stream()
                        .map(o -> {
                            UUID productId = (UUID) o[0];
                            double price = (double) o[1];
                            double toBuyAmount = (double) o[3];
                            boolean isPurchased = (boolean) o[2];
                            Product product = productDao.findById(productId).get();

                            double totalProductAmount = (double) entityManager.createNativeQuery(
                                            "select sum(cp.amount) from cocktail_product cp " +
                                                    "where cp.product_id = :productId "
                                    ).setParameter("productId", productId)
                                    .getSingleResult();

                            return ShoppingItemResponse.builder()
                                    .tag(product.getTag())
                                    .name(product.getName())
                                    .productId(productId)
                                    .unit(product.getUnit())
                                    .isPurchased(isPurchased)
                                    .amount(totalProductAmount * guestsCount)
                                    .price(toBuyAmount * price)
                                    .build();
                        })
                        .toList();
    }

    @Override
    @Transactional
    public void updatePurchased(UUID eventId, UUID productId) {
        entityManager.createNativeQuery(
                        "update event_product " +
                                "set is_purchased = not is_purchased " +
                                "where event_id = :eventId and product_id = :productId"
                ).setParameter("eventId", eventId)
                .setParameter("productId", productId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public ShoppingItemInfoResponse getInfoAboutProduct(UUID eventId, UUID productId) {
        Double totalAmount = productService.getTotalProductAmount(productId);
        Product product = productDao.findById(productId).get();

        List<Object[]> productInfo = entityManager.createNativeQuery(
                        "select ep.price, ep.to_buy_amount " +
                                "from event_product ep " +
                                "where ep.event_id = :eventId and ep.product_id = :productId"
                ).setParameter("eventId", eventId)
                .setParameter("productId", productId)
                .getResultList();

        double price = (double) productInfo.getFirst()[0];
        double toBuyAmount = (double) productInfo.getFirst()[1];

        return ShoppingItemInfoResponse.builder()
                .productId(productId)
                .name(product.getName())
                .unit(product.getUnit())
                .totalAmount(totalAmount)
                .price(price)
                .toBuyAmount(toBuyAmount)
                .build();
    }
}
