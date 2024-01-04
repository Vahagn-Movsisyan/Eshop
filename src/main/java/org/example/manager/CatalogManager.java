package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.Catalog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogManager {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void addCatalogManager(Catalog catalog) {
        String sql = "INSERT INTO catalog(catalog_name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, catalog.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                catalog.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Catalog> getCatalogs() {
        String query = "SELECT * FROM catalog";
        List<Catalog> catalogs = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("catalog_id");
                String name = resultSet.getString("catalog_name");
                catalogs.add(Catalog.builder().id(id).name(name).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogs;
    }

    public Catalog getCatalogByName(String name) {
        String query = "SELECT * FROM catalog WHERE catalog_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("catalog_name");
                return Catalog.builder().id(id).name(name).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Catalog getCatalogById(int id) {
        String query = "SELECT * FROM catalog WHERE catalog_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("catalog_id");
                return Catalog.builder().id(id).name(name).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
