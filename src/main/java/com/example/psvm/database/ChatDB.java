package com.example.psvm.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChatDB {
    public boolean sendMessage(String message) {
        String query = "INSERT INTO bericht (bericht) VALUES (?)";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, message);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Fout bij sturen van bericht: " + e.getMessage());
            return false;
        }
    }
}
