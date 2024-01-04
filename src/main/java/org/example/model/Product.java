package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Category category;
    private List<Catalog> catalogs;

    public Product(String name, String description, double price, int quantity, Category category, List<Catalog> catalogs) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.catalogs = catalogs;
    }
}
