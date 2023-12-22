package org.example;

import org.example.manager.CategoryManager;
import org.example.manager.ProductManager;
import org.example.model.Category;
import org.example.model.Product;

import java.util.Scanner;

public class Main implements Command {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CategoryManager CATEGORY_MANAGER = new CategoryManager();
    private static final ProductManager PRODUCT_MANAGER = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;

        while (isRun) {
            Command.printCommands();
            String choice = SCANNER.nextLine();
            switch (choice) {
                case EXIT -> isRun = false;
                case ADD_CATEGORY -> addCategory();
                case EDIT_CATEGORY_BY_ID -> editCategoryById();
                case DELETE_CATEGORY_BY_ID -> deleteCategoryById();
                case ADD_PRODUCT -> addProduct();
                case EDIT_PRODUCT -> editProduct();
                case DELETE_PRODUCT_BY_ID -> deleteProductById();
                case SUM_OF_PRODUCT -> PRODUCT_MANAGER.printCountOfProduct();
                case MAX_PRICE_OF_PRODUCT -> PRODUCT_MANAGER.printMaxPriceOfProduct();
                case MIN_PRICE_OF_PRODUCT -> PRODUCT_MANAGER.printMinPriceOfProduct();
                case AVG_PRICE_OF_PRODUCT -> PRODUCT_MANAGER.printAvgPriceOfProduct();
                case GET_ALL_PRODUCTS -> printAllProducts();
                case GET_ALL_CATEGORY -> printAllCategory();
                default -> System.out.println("Invalid choice. Please try again!");
            }
        }
    }
    private static void addCategory() {
        System.out.println("Enter category name");
        String categoryName = SCANNER.nextLine();
        Category category = new Category(categoryName);
        CATEGORY_MANAGER.categoryManagerAdd(category);
        System.out.println("Category " + categoryName + " is successfully added");
    }

    private static void editCategoryById() {
        printAllCategory();
        System.out.println("Enter category id");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Enter the name");
        String newName = SCANNER.nextLine();
        CATEGORY_MANAGER.categoryManagerEdit(id, newName);
    }

    private static void deleteCategoryById() {
        printAllCategory();
        System.out.println("Enter category id for delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        CATEGORY_MANAGER.categoryManagerDeleteById(id);
    }

    private static void addProduct() {
        System.out.println("Enter product details (name description price quantity category):");
        String inputLine = SCANNER.nextLine();
        String[] inputArray = inputLine.split("\\s+");

        // To avoid arrayIndexOutOfBoundsException
        if (inputArray.length == 5) {
            String name = inputArray[0];
            String description = inputArray[1];
            double price = Double.parseDouble(inputArray[2]);
            int quantity = Integer.parseInt(inputArray[3]);
            String category = inputArray[4];

            Product product = new Product(name, description, price, quantity, category);
            PRODUCT_MANAGER.productManagerAdd(product);
            System.out.println("Product  with " + name + " is successfully added");
        } else {
            System.out.println("Invalid input. Please try again!");
        }
    }

    private static void editProduct() {
        printAllProducts();
        System.out.println("Enter the ID of the product you want to edit:");
        int id = Integer.parseInt(SCANNER.nextLine());
        Product existingProduct = PRODUCT_MANAGER.getProductById(id);

        if (existingProduct == null) {
            System.out.println("Product with ID " + id + " not found!");
        } else {
            System.out.println("Existing product details:");
            System.out.println(existingProduct);

            System.out.println("Enter new product details (name description price quantity category):");
            String inputLine = SCANNER.nextLine();
            String[] inputArray = inputLine.split("\\s+");

            // To avoid arrayIndexOutOfBoundsException
            if (inputArray.length == 5) {
                String name = inputArray[0];
                String description = inputArray[1];
                double price = Double.parseDouble(inputArray[2]);
                int quantity = Integer.parseInt(inputArray[3]);
                String category = inputArray[4];
                PRODUCT_MANAGER.productManagerEditByID(id, name, description, price, quantity, category);
                System.out.println("Product with ID " + id + " updated successfully!");
            }
        }
    }

    private static void deleteProductById() {
        printAllProducts();
        System.out.println("Enter product id for delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        PRODUCT_MANAGER.productManagerDeleteById(id);
    }

    private static void printAllCategory() {
        for (Category category : CATEGORY_MANAGER.getAllCategory()) {
            System.out.println(category);
        }
    }

    private static void printAllProducts() {
        for (Product product : PRODUCT_MANAGER.getAllProduct()) {
            System.out.println(product);
        }
    }
}
