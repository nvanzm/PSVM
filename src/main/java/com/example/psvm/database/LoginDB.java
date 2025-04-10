package com.example.psvm.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginDB {

    public Optional<Integer> getUserIdByUsername(String username) {
        String query = "SELECT id FROM user WHERE naam = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen van gebruiker ID: " + e.getMessage());
            throw new RuntimeException("Databasefout bij ophalen van gebruiker ID", e);
        }
    }

    public Optional<String> getUsernameById(int id) {
        String query = "SELECT naam FROM user WHERE id = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getString("naam"));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen van gebruiker ID: " + e.getMessage());
            throw new RuntimeException("Databasefout bij ophalen van gebruiker ID", e);
        }
    }

}
