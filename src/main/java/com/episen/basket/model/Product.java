package com.episen.basket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long gtin;
    private String label;
    private Double unitPrice;
    private Double vat;
    private Integer quantity;
}
