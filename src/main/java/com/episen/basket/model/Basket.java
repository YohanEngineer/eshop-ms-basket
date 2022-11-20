package com.episen.basket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    private String userEmail;
    private Double totalAmount;
    private String status;
    private List<Product> items;
}
