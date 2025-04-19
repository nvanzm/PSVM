package com.example.psvm.database;

import com.example.psvm.model.Epic;
import com.example.psvm.model.Taak;
import com.example.psvm.model.UserStory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The WorkItemDB class provides methods to interact with the database for managing
 * work items such as epics, user stories, and tasks. This class allows retrieval of
 * work items by team ID, adding new work items, and fetching work item IDs by name and type.
 */
public class WorkItemDB {

    public List<Epic> getAllEpics(int teamID) {
        List<Epic> epics = new ArrayList<>();
        String query = "SELECT id, naam, beschrijving, team_id FROM epic WHERE team_id=?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teamID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Epic epic = new Epic(
                        resultSet.getInt("id"),
                        resultSet.getString("naam"),
                        resultSet.getString("beschrijving")
                );
                epics.add(epic);
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen epics: " + e.getMessage());
        }

        return epics;
    }


    public List<UserStory> getAllUserstories(int teamID) {
        List<UserStory> userstories = new ArrayList<>();
        String query = "SELECT id, naam, beschrijving, epic_id, team_id FROM user_story WHERE team_id = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teamID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserStory userStory = new UserStory(
                        resultSet.getInt("id"),
                        resultSet.getString("naam"),
                        resultSet.getString("beschrijving"),
                        resultSet.getInt("epic_id")
                );
                userstories.add(userStory);
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen userstories: " + e.getMessage());
        }

        return userstories;
    }


    public List<Taak> getAllTaken(int teamID) {
        List<Taak> taken = new ArrayList<>();
        String query = "SELECT id, naam, beschrijving, user_story_id FROM taak WHERE team_id=?"; // Assuming 'id' is a column too

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teamID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Taak taak = new Taak(
                        resultSet.getInt("id"),
                        resultSet.getString("naam"),
                        resultSet.getString("beschrijving"),
                        resultSet.getInt("user_story_id")
                );
                taken.add(taak);
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen taken: " + e.getMessage());
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


