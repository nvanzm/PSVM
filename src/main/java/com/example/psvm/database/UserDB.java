package com.example.psvm.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDB {
    public Optional<Integer> getIDFromTeamName(String teamName) {
        String query = "SELECT id FROM team WHERE naam = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, teamName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getInt("id"));
                } else {
                    return Optional.empty(); // Geen team met die naam gevonden
                }
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen teamID: " + e.getMessage());
            throw new RuntimeException("Databasefout bij ophalen teamID: ", e);
        }
    }



    public boolean addUserToTeam(int userID, int teamID) {
        String query = "UPDATE user SET team_id = ? WHERE id = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teamID);
            preparedStatement.setInt(2, userID);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Fout bij toevoegen user: " + e.getMessage());
            throw new RuntimeException("Databasefout bij toevoegen user: ", e);
        }
    }

}
