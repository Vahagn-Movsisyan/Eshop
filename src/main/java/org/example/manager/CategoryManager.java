package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void categoryManagerAdd(Category category) {
        String query = "INSERT INTO category(category_name) VALUES(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void categoryManagerEdit(int id, String newCategoryName) {
        if (getCategoryById(id) == null) {
            System.out.println("Category with " + id + " dose not found!");
            return;
        }
        String query = "UPDATE category SET category_name = ? WHERE category_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newCategoryName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Category with " + id + " updated to " + newCategoryName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void categoryManagerDeleteById(int id) {
        if (getCategoryById(id) == null) {
            System.out.println("Category with " + id + " dose not found!");
            return;
        }
        String query = "DELETE FROM category WHERE category_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Category with " + id + " successfully deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getCategoryByName(String categoryName) {
        String query = "SELECT * FROM category WHERE category_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("category_id");
                return new Category(id, categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category getCategoryById(int id) {
        String query = "SELECT * FROM category WHERE category_id = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String name = resultSet.getString("category_name");
                return new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                String categoryName = resultSet.getString("category_name");
                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
