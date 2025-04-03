package com.example.psvm.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    public List<String> getAllUserNames() {
        List<String> userNames = new ArrayList<>();

        String query = "SELECT naam FROM user";

        try (Connection connection = ConnectionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                userNames.add(resultSet.getString("naam"));
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen van gebruikers: " + e.getMessage());
        }

        return userNames;
    }
}
