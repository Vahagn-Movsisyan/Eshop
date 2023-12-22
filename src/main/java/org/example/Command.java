package org.example;

public interface Command {
    String EXIT = "0";
    String ADD_CATEGORY = "1";
    String EDIT_CATEGORY_BY_ID = "2";
    String DELETE_CATEGORY_BY_ID = "3";
    String ADD_PRODUCT = "4";
    String EDIT_PRODUCT = "5";
    String DELETE_PRODUCT_BY_ID = "6";
    String SUM_OF_PRODUCT = "7";
    String MAX_PRICE_OF_PRODUCT = "8";
    String MIN_PRICE_OF_PRODUCT = "9";
    String AVG_PRICE_OF_PRODUCT = "10";
    String GET_ALL_PRODUCTS = "11";
    String GET_ALL_CATEGORY = "12";

    static void printCommands() {
        System.out.println(EXIT + ": Exit");
        System.out.println(ADD_CATEGORY + ": Add Category");
        System.out.println(EDIT_CATEGORY_BY_ID + ": Edit Category by ID");
        System.out.println(DELETE_CATEGORY_BY_ID + ": Delete Category by ID");
        System.out.println(ADD_PRODUCT + ": Add Product");
        System.out.println(EDIT_PRODUCT + ": Edit Product");
        System.out.println(DELETE_PRODUCT_BY_ID + ": Delete Product by ID");
        System.out.println(SUM_OF_PRODUCT + ": Sum of Product Prices");
        System.out.println(MAX_PRICE_OF_PRODUCT + ": Max Price of Product");
        System.out.println(MIN_PRICE_OF_PRODUCT + ": Min Price of Product");
        System.out.println(AVG_PRICE_OF_PRODUCT + ": Avg Price of Product");
        System.out.println(GET_ALL_PRODUCTS + ": Get All Products");
        System.out.println(GET_ALL_CATEGORY + ": Get All Categories");
    }
}
