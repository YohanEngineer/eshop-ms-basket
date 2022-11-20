package com.episen.basket.service;

import com.episen.basket.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProductService {
    Product saveProduct(Product product, HttpServletRequest request, HttpServletResponse response);

    Product saveProduct(Product product);

    Product getProductByLabel(String username, HttpServletRequest request, HttpServletResponse response);

    List<Product> getProducts(HttpServletRequest request, HttpServletResponse response);

    void removeProduct(String label, HttpServletRequest request, HttpServletResponse response);

    Product updateProduct(Product product, HttpServletRequest request, HttpServletResponse response);
}
