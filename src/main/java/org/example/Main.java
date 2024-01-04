package org.example;

import org.example.command.Command;
import org.example.manager.CatalogManager;
import org.example.manager.CategoryManager;
import org.example.manager.ProductManager;
import org.example.manager.UserManager;
import org.example.model.Catalog;
import org.example.model.Category;
import org.example.model.Product;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Command {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CategoryManager CATEGORY_MANAGER = new CategoryManager();
    private static final ProductManager PRODUCT_MANAGER = new ProductManager();
    private static final CatalogManager CATALOG_MANAGER = new CatalogManager();
    private static final UserManager USER_MANAGER = new UserManager();

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
                case ADD_CATALOG -> addCatalog();
                case SUM_OF_PRODUCT -> PRODUCT_MANAGER.printCountOfProduct();
                case MAX_PRICE_OF_PRODUCT -> PRODUCT_MANAGER.printMaxPriceOfProduct();
                case MIN_PRICE_OF_PRODUCT -> PRODUCT_MANAGER.printMinPriceOfProduct();
                case AVG_PRICE_OF_PRODUCT -> PRODUCT_MANAGER.printAvgPriceOfProduct();
                case GET_ALL_PRODUCTS -> printAllProducts();
                case GET_ALL_CATEGORY -> printAllCategory();
                case GET_ALL_CATALOG -> printAllCatalogs();
                case ADD_USER -> addUser();
                case WATCH_PRODUCTS -> watchProduct();
                default -> System.out.println("Invalid choice. Please try again!");
            }
        }
    }

    private static void watchProduct() {
        int limitValue = 5;
        int offsetValue = 0;
        boolean continueWatchProducts = true;
        while (continueWatchProducts) {
            List<Product> products = PRODUCT_MANAGER.watchProducts(limitValue, offsetValue);
            for (Product product : products) {
                System.out.println(product);
            }
            System.out.println("> (next) | < (back) ! (exit)");
            String select = SCANNER.nextLine();
            switch (select) {
                case ">" -> offsetValue += limitValue;
                case "<" -> offsetValue -= limitValue;
                case "!" -> continueWatchProducts = false;
                default -> System.out.println("You are entered an error!");
            }
        }
    }

    private static void addUser() {
        System.out.println("Enter your name, email, password by(space)");
        String inputLine = SCANNER.nextLine();
        String[] inputArr = inputLine.split("\\s+");

        // To avoid arrayIndexOutOfBoundsException
        if (inputArr.length != 3) {
            System.out.println("Error: Try again, you must write 3 values you write " + inputArr.length + " values");
            return;
        }
        String name = inputArr[0];
        String email = inputArr[1];
        String password = inputArr[2];
        USER_MANAGER.addUser(User.builder().name(name).email(email).password(password).build());
    }

    private static void addCategory() {
        System.out.println("Enter category name");
        String categoryName = SCANNER.nextLine();
        if (CATEGORY_MANAGER.getCategoryByName(categoryName) == null) {
            Category category = new Category(categoryName);
            CATEGORY_MANAGER.categoryManagerAdd(category);
            System.out.println("Category " + categoryName + " is successfully added");
        } else {
            System.out.println("Category with " + categoryName + " is already added!");
        }
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
        List<Catalog> catalogById = new ArrayList<>();

        System.out.println("Enter catalog ids:");
        printAllCatalogs();
        String catalogId = SCANNER.nextLine();
        String[] catalogArr = catalogId.split("\\s+");
        for (String selectedCatalogs : catalogArr) {
            catalogById.add(CATALOG_MANAGER.getCatalogById(Integer.parseInt(selectedCatalogs)));
        }

        System.out.println("Enter product details (name description price quantity category(id)):");
        System.out.println("Available Categories:");
        printAllCategory();

        String inputLine = SCANNER.nextLine();
        String[] inputArray = inputLine.split("\\s+");

        // To avoid arrayIndexOutOfBoundsException
        if (inputArray.length == 5) {
            String name = inputArray[0];
            String description = inputArray[1];
            double price = Double.parseDouble(inputArray[2]);
            int quantity = Integer.parseInt(inputArray[3]);
            int category_id = Integer.parseInt(inputArray[4]);

            Category category = CATEGORY_MANAGER.getCategoryById(category_id);
            Product product = new Product(name, description, price, quantity, category, catalogById);

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
                int category_id = Integer.parseInt(inputArray[4]);

                Category category = CATEGORY_MANAGER.getCategoryById(category_id);
                PRODUCT_MANAGER.productManagerEditByID(id, name, description, price, quantity, category.getId());
                System.out.println("Product with ID " + id + " updated successfully!");
            }
        }
    }

    private static void deleteProductById() {
        printAllProducts();
        System.out.println("Enter product id for delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        PRODUCT_MANAGER.productManagerDeleteById(id);
        System.out.println("User successfully added!");
    }

    private static void addCatalog() {
        System.out.println("Enter the catalog name");
        String catalogName = SCANNER.nextLine();
        CATALOG_MANAGER.addCatalogManager(Catalog.builder().name(catalogName).build());
    }

    private static void printAllCatalogs() {
        for (Catalog catalog : CATALOG_MANAGER.getCatalogs()) {
            System.out.println(catalog);
        }
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
