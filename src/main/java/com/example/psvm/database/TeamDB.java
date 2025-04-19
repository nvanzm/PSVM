package com.example.psvm.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The TeamDB class provides methods for interacting with the team-related data
 * stored in the database. It includes functionality for adding a new team,
 * retrieving all team names, and fetching a team name by its identifier.
 */
public class TeamDB {
    public Optional<Integer> addNewTeamToDB(String teamNaam) {
        String query = "INSERT INTO team (naam) VALUES (?)";

            try (Connection connection = ConnectionDB.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, teamNaam);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Optional.of(generatedKeys.getInt(1));
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            System.err.println("Fout bij toevoegen team: " + e.getMessage());
            throw new RuntimeException("Databasefout bij toevoegen team: ", e);
        }
    }

    public List<String> getAllTeamNames() {
        List<String> teams = new ArrayList<>();
        String query = "SELECT naam FROM team";

        try (Connection connection = ConnectionDB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                teams.add(resultSet.getString("naam"));
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen teams: " + e.getMessage());
        }

        return teams;
    }

    public Optional<String> getTeamNameById(int team_id) {
        String query = "SELECT naam FROM team WHERE id = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, team_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getString("naam"));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen van de teamnaam: " + e.getMessage());
            throw new RuntimeException("Databasefout bij ophalen van de teamnaam", e);
        }
    }
}

