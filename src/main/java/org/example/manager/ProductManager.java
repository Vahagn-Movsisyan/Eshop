package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.Catalog;
import org.example.model.Category;
import org.example.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static final CategoryManager categoryManager = new CategoryManager();
    private static final CatalogManager catalogManager = new CatalogManager();
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void productManagerAdd(Product product) {
        String query = "INSERT INTO product(product_name, product_description, product_price,product_quantity,product_category) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
            addProductCatalogs(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void productManagerEditByID(int id, String name, String description, double price, int qty, int category_id) {
        if (getProductById(id) == null) {
            System.out.println("Product with " + id + " id dose not found!");
            return;
        }
        String query = "UPDATE product SET product_name = ?, product_description = ?, product_price = ?, product_quantity = ?, product_category = ? WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, qty);
            preparedStatement.setInt(5, category_id);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
            System.out.println("Product with " + id + " successfully edited");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void productManagerDeleteById(int id) {
        if (getProductById(id) == null) {
            System.out.println("Product with " + id + " id dose not found!");
            return;
        }
        String query = "DELETE FROM product WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Product with " + id + " successfully deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        String query = "SELECT * FROM product WHERE product_id =" + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("product_description");
                double price = resultSet.getDouble("product_price");
                int quantity = resultSet.getInt("product_quantity");
                int category_id = resultSet.getInt("product_category");
                Category category = categoryManager.getCategoryById(category_id);
                List<Catalog> productCatalog = getProductCatalog(id);
                return new Product(id, name, description, price, quantity, category, productCatalog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProduct() {
        List<Product> tmpProductList = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("product_description");
                double price = resultSet.getDouble("product_price");
                int quantity = resultSet.getInt("product_quantity");
                int category_id = resultSet.getInt("product_category");
                Category category = categoryManager.getCategoryById(category_id);
                List<Catalog> productCatalog = getProductCatalog(id);
                tmpProductList.add(new Product(id, name, description, price, quantity, category, productCatalog));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmpProductList;
    }

    public List<Product> watchProducts(int limitValue, int offsetValue) {
        List<Product> tmpProductList = new ArrayList<>();
        String query = "SELECT * FROM product ORDER BY product_id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, limitValue);
            preparedStatement.setInt(2, offsetValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("product_description");
                double price = resultSet.getDouble("product_price");
                int quantity = resultSet.getInt("product_quantity");
                int category_id = resultSet.getInt("product_category");
                Category category = categoryManager.getCategoryById(category_id);
                List<Catalog> productCatalog = getProductCatalog(id);
                tmpProductList.add(new Product(id, name, description, price, quantity, category, productCatalog));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmpProductList;
    }

    public void printCountOfProduct() {
        printAggregateValue("COUNT", "product_count");
    }

    public void printMaxPriceOfProduct() {
        printAggregateValue("MAX", "max_price");
    }

    public void printMinPriceOfProduct() {
        printAggregateValue("MIN", "min_price");
    }

    public void printAvgPriceOfProduct() {
        printAggregateValue("AVG", "avg_price");
    }

    private List<Catalog> getProductCatalog(int productId) {
        String query = "SELECT * FROM product_catalog WHERE product_id = ?";
        List<Catalog> catalogs = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int catalogId = resultSet.getInt("catalog_id");
                catalogs.add(catalogManager.getCatalogById(catalogId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogs;
    }

    private void printAggregateValue(String aggregateFunction, String resultName) {
        String query = "SELECT " + aggregateFunction + "(product_price) AS " + resultName + " FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int result = resultSet.getInt(resultName);
                System.out.println(resultName + " of products is " + result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addProductCatalogs(Product product) {
        if (product.getCatalogs() != null && !product.getCatalogs().isEmpty()) {
            for (Catalog catalog : product.getCatalogs()) {
                String sql = "INSERT INTO product_catalog(product_id, catalog_id) VALUES(?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, product.getId());
                    preparedStatement.setInt(2, catalog.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
