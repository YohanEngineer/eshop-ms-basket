package com.episen.basket.service;

import com.episen.basket.model.Basket;
import com.episen.basket.model.Product;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BasketService {
    Basket saveBasket(Basket user, HttpServletRequest request, HttpServletResponse response);

    Product addProductToBasket(String userEmail, Product product, HttpServletRequest request, HttpServletResponse response);

    Basket getBasketByUserEmail(String username, HttpServletRequest request, HttpServletResponse response);

    List<Basket> getBaskets(HttpServletRequest request, HttpServletResponse response);

    Basket updateBasket(Basket basket, HttpServletRequest request, HttpServletResponse response);

    void removeBasket(String userEmail, HttpServletRequest request, HttpServletResponse response);


}
