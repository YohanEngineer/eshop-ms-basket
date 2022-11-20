package com.episen.basket.repository;

import com.episen.basket.model.Basket;
import com.episen.basket.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class BasketRepository {
    private Map<String, Basket> basketInMemory = new HashMap<>();

    public Basket add(Basket basket) {
        log.info("Adding new basket of : {}", basket.getUserEmail());

        basketInMemory.put(basket.getUserEmail(), basket);

        return basket;
    }

    public Product addProduct(Basket basket, Product product) {
        basket.getItems().add(product);
        basketInMemory.put(basket.getUserEmail(), basket);
        return product;
    }

    public Basket getBasketByUser(String userEmail) {
        return basketInMemory.get(userEmail);
    }

    public List<Basket> getAll() {

        return new ArrayList<>(basketInMemory.values());
    }

    public Basket update(Basket basketToUpdate) {
        basketInMemory.put(basketToUpdate.getUserEmail(), basketToUpdate);
        return basketToUpdate;
    }

    public void delete(String userEmail) {
        basketInMemory.remove(userEmail);
    }
}
