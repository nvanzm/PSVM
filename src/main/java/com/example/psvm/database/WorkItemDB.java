package com.example.psvm.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkItemDB {

    public List<String> getAllEpics() {
        List<String> epics = new ArrayList<>();
        String query = "SELECT naam FROM epic";

        try (Connection connection = ConnectionDB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                epics.add(resultSet.getString("naam"));
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen teams: " + e.getMessage());
        }

        return epics;
    }

    public List<String> getAllUserstories() {
        List<String> userstories = new ArrayList<>();
        String query = "SELECT naam FROM user_story";

        try (Connection connection = ConnectionDB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                userstories.add(resultSet.getString("naam"));
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen teams: " + e.getMessage());
        }

        return userstories;
    }

    public List<String> getAllTaken() {
        List<String> taken = new ArrayList<>();
        String query = "SELECT naam FROM taak";

        try (Connection connection = ConnectionDB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                taken.add(resultSet.getString("naam"));
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen teams: " + e.getMessage());
        }

        return taken;
    }

    public Optional<Integer> addNewEpic(String Wnaam, String Wbeschrijving, int Wteam_id) {
                String query = "INSERT INTO epic (naam, beschrijving, team_id) VALUES (?,?,?)";

                try (Connection connection = ConnectionDB.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setString(1, Wnaam);
                    preparedStatement.setString(2, Wbeschrijving);
                    preparedStatement.setInt(3, Wteam_id);

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
                    System.err.println("Fout bij toevoegen Workitem: " + e.getMessage());
                    throw new RuntimeException("Databasefout bij toevoegen Workitem: ", e);
                }
    }

    public Optional<Integer> addNewUserStory(String Wnaam, String Wbeschrijving,int epic_id, int Wteam_id) {
        String query = "INSERT INTO user_story (naam, beschrijving, epic_id, team_id) VALUES (?,?,?,?)";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, Wnaam);
            preparedStatement.setString(2, Wbeschrijving);
            preparedStatement.setInt(3, epic_id);
            preparedStatement.setInt(4, Wteam_id);

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
            System.err.println("Fout bij toevoegen Workitem: " + e.getMessage());
            throw new RuntimeException("Databasefout bij toevoegen Workitem: ", e);
        }
    }
    public Optional<Integer> addNewTaak(String Wnaam, String Wbeschrijving,int user_story_id, int Wteam_id) {
        String query = "INSERT INTO taak (naam, beschrijving, user_story_id, team_id) VALUES (?,?,?,?)";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, Wnaam);
            preparedStatement.setString(2, Wbeschrijving);
            preparedStatement.setInt(3, user_story_id);
            preparedStatement.setInt(4, Wteam_id);

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
            System.err.println("Fout bij toevoegen Workitem: " + e.getMessage());
            throw new RuntimeException("Databasefout bij toevoegen Workitem: ", e);
        }
    }

    public Optional<Integer> getWorkItemIdByName(String name, String type) {
        String query = "SELECT id FROM "+ type +" WHERE naam = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen van het id: " + e.getMessage());
            throw new RuntimeException("Databasefout bij ophalen van het workitem", e);
        }
    }
}


