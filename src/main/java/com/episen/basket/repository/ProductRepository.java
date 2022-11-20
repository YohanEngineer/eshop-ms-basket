package com.episen.basket.repository;

import com.episen.basket.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ProductRepository {
    private Map<String, Product> productInMemory = new HashMap<>();

    public Product add(Product product) {
        productInMemory.put(product.getLabel(), product);

        return product;
    }

    public Product getProductByLabel(String label) {
        return productInMemory.get(label);
    }

    public List<Product> getAll() {
        return new ArrayList<>(productInMemory.values());
    }

    public Product update(Product productToUpdate) {
        productInMemory.put(productToUpdate.getLabel(), productToUpdate);
        return productToUpdate;
    }

    public void delete(String label) {
        productInMemory.remove(label);
    }


}
