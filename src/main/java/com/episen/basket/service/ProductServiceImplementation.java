package com.episen.basket.service;

import com.episen.basket.model.Product;
import com.episen.basket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.episen.basket.security.Authorization.checkAuthorization;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        if (StringUtils.isEmpty(product.getLabel()) || StringUtils.isEmpty(product.getGtin())) {
            throw new RuntimeException("Product exception");
        }
        if (productRepository.getProductByLabel(product.getLabel()) != null) {
            throw new RuntimeException("Product already exist");
        }
        return productRepository.add(product);
    }

    @Override
    public Product saveProduct(Product product) {
        if (StringUtils.isEmpty(product.getLabel()) || StringUtils.isEmpty(product.getGtin())) {
            throw new RuntimeException("Product exception");
        }
        if (productRepository.getProductByLabel(product.getLabel()) != null) {
            throw new RuntimeException("Product already exist");
        }
        log.info("Adding new product : {}", product.getLabel());
        return productRepository.add(product);
    }

    @Override
    public Product getProductByLabel(String label, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        Product product = productRepository.getProductByLabel(label);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        log.info("Retrieving product with label : " + label);
        return productRepository.getProductByLabel(label);
    }

    @Override
    public List<Product> getProducts(HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        log.info("Retrieving all products.");
        return productRepository.getAll();
    }

    @Override
    public Product updateProduct(Product product, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        Product productToBeUpdated = productRepository.getProductByLabel(product.getLabel());
        if (productToBeUpdated == null) {
            throw new RuntimeException("Product not found");
        }
        log.info("Updating product...");
        return productRepository.update(product);
    }

    @Override
    public void removeProduct(String label, HttpServletRequest request, HttpServletResponse response) {
        verifyToken(request, response);
        Product productToBeDeleted = productRepository.getProductByLabel(label);
        if (productToBeDeleted == null) {
            throw new RuntimeException("Product not found");
        }
        log.info("Deleting product...");
        productRepository.delete(label);
    }

    private void verifyToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!checkAuthorization(true,request, response)) {
                throw new RuntimeException("Invalid token");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
