package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String product_category;

    public Product(String name, String description, double price, int quantity, String product_category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.product_category = product_category;
    }
}
