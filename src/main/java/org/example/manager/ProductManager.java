package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void productManagerAdd(Product product) {
        String query = "INSERT INTO product(product_name, product_description, product_price,product_quantity,product_category) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setString(5, product.getProduct_category());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void productManagerEditByID(int id, String name, String description, double price, int qty, String category) {
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
            preparedStatement.setString(5, category);
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

    public Product getProductById(int id) {
        String query = "SELECT * FROM product WHERE product_id =" + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("product_description");
                double price = resultSet.getDouble("product_price");
                int quantity = resultSet.getInt("product_quantity");
                String category = resultSet.getString("product_category");
                return new Product(id, name, description, price, quantity, category);
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
                String category = resultSet.getString("product_category");
                Product product = new Product(id, name, description, price, quantity, category);
                tmpProductList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmpProductList;
    }
}
