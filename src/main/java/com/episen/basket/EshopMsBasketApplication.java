package com.episen.basket;

import com.episen.basket.model.Product;
import com.episen.basket.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class EshopMsBasketApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopMsBasketApplication.class, args);
        log.info("Basket API started...");
    }

    @Bean
    CommandLineRunner run(ProductService productService) {
        return args -> {
            Product ps5 = new Product(159753258456L, "PS5", 549.99, 0.20, 100);
            Product xboxSerieX = new Product(159364958456L, "Xbox Series X", 499.99, 0.20, 500);
            Product nintendoSwitch = new Product(159776538456L, "Nintendo Switch", 549.99, 0.20, 20);
            Product macBookAir = new Product(151073258456L, "Mac Book Air M1", 1199.99, 0.20, 1000);
            productService.saveProduct(ps5);
            productService.saveProduct(xboxSerieX);
            productService.saveProduct(nintendoSwitch);
            productService.saveProduct(macBookAir);
        };
    }

}
