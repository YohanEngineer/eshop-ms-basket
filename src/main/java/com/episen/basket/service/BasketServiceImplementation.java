package com.episen.basket.service;

import com.episen.basket.model.Basket;
import com.episen.basket.model.Product;
import com.episen.basket.repository.BasketRepository;
import com.episen.basket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.episen.basket.security.Authorization.checkAuthorization;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketServiceImplementation implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    @Override
    public Basket saveBasket(Basket basket, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        if (StringUtils.isEmpty(basket.getUserEmail())) {
            throw new RuntimeException("Basket exception");
        }
        if (basketRepository.getBasketByUser(basket.getUserEmail()) != null) {
            throw new RuntimeException("Basket already exist");
        }
        log.info("Adding new basket for : {}", basket.getUserEmail());
        return basketRepository.add(basket);
    }

    @Override
    public Product addProductToBasket(String userEmail, Product product, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        Basket searchBasket = basketRepository.getBasketByUser(userEmail);
        Product searchProduct = productRepository.getProductByLabel(product.getLabel());
        if (searchBasket == null || searchProduct == null) {
            throw new RuntimeException("Basket or product not found");
        }
        log.info("Adding new product {} in basket of : {}", product.getLabel(), userEmail);
        return basketRepository.addProduct(searchBasket, product);
    }

    @Override
    public Basket getBasketByUserEmail(String userEmail, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        Basket basket = basketRepository.getBasketByUser(userEmail);
        if (basket == null) {
            throw new RuntimeException("Basket not found");
        }
        log.info("Retrieving basket with userEmail : " + userEmail);
        return basket;
    }

    @Override
    public List<Basket> getBaskets(HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        log.info("Retrieving all baskets.");
        return basketRepository.getAll();
    }

    @Override
    public Basket updateBasket(Basket basket, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        Basket basketToBeUpdate = basketRepository.getBasketByUser(basket.getUserEmail());
        if (basketToBeUpdate == null) {
            throw new RuntimeException("Basket not found");
        }
        log.info("Updating basket...");
        return basketRepository.update(basket);
    }

    @Override
    public void removeBasket(String userEmail, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        Basket basketToBeDelete = basketRepository.getBasketByUser(userEmail);
        if (basketToBeDelete == null) {
            throw new RuntimeException("Basket not found");
        }
        log.info("Deleting basket...");
        basketRepository.delete(userEmail);
    }

    private void verifyToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!checkAuthorization(false, request, response)) {
                throw new RuntimeException("Invalid token");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
