package org.example.command;

import java.nio.file.Watchable;

public interface Command {
    String EXIT = "0";
    String ADD_CATEGORY = "1";
    String EDIT_CATEGORY_BY_ID = "2";
    String DELETE_CATEGORY_BY_ID = "3";
    String ADD_PRODUCT = "4";
    String EDIT_PRODUCT = "5";
    String DELETE_PRODUCT_BY_ID = "6";
    String ADD_CATALOG = "7";
    String SUM_OF_PRODUCT = "8";
    String MAX_PRICE_OF_PRODUCT = "9";
    String MIN_PRICE_OF_PRODUCT = "10";
    String AVG_PRICE_OF_PRODUCT = "11";
    String GET_ALL_PRODUCTS = "12";
    String GET_ALL_CATEGORY = "13";
    String GET_ALL_CATALOG = "14";
    String ADD_USER = "15";
    String WATCH_PRODUCTS = "16";

    static void printCommands() {
        System.out.println(EXIT + ": Exit");
        System.out.println(ADD_CATEGORY + ": Add Category");
        System.out.println(EDIT_CATEGORY_BY_ID + ": Edit Category by ID");
        System.out.println(DELETE_CATEGORY_BY_ID + ": Delete Category by ID");
        System.out.println(ADD_PRODUCT + ": Add Product");
        System.out.println(EDIT_PRODUCT + ": Edit Product");
        System.out.println(DELETE_PRODUCT_BY_ID + ": Delete Product by ID");
        System.out.println(ADD_CATALOG + ": Add catalog");
        System.out.println(SUM_OF_PRODUCT + ": Sum of Product Prices");
        System.out.println(MAX_PRICE_OF_PRODUCT + ": Max Price of Product");
        System.out.println(MIN_PRICE_OF_PRODUCT + ": Min Price of Product");
        System.out.println(AVG_PRICE_OF_PRODUCT + ": Avg Price of Product");
        System.out.println(GET_ALL_PRODUCTS + ": Get All Products");
        System.out.println(GET_ALL_CATEGORY + ": Get All Categories");
        System.out.println(GET_ALL_CATALOG + ": Get All Catalogs");
        System.out.println(ADD_USER + ": Add user");
        System.out.println(WATCH_PRODUCTS + ": Watch products");
    }
}
