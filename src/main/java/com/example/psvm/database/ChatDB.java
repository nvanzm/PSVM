package com.example.psvm.database;

import com.example.psvm.model.Epic;
import com.example.psvm.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ChatDB class provides methods to interact with the database for managing and retrieving
 * chat messages within a team. It includes functionality to send messages and fetch messages
 * belonging to a specific team.
 */
public class ChatDB {

    public boolean sendMessage(int userId, String message, int team_id, Integer itemId, String itemType) {
        String query = "INSERT INTO bericht (user_id, bericht, team_id, epic_id, user_story_id, taak_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, team_id);

            if ("Epic".equals(itemType)) {
                preparedStatement.setInt(4, itemId);
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            } else if ("UserStory".equals(itemType)) {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
                preparedStatement.setInt(5, itemId);
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            } else if ("Taak".equals(itemType)) {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
                preparedStatement.setInt(6, itemId);
            } else {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Fout bij sturen van bericht: " + e.getMessage());
            throw new RuntimeException("Fout bij versturen van bericht");
        }
    }

    public List<Message> getMessages(int team_id) {
        String query = "SELECT id, bericht, user_id, team_id, epic_id, user_story_id, taak_id FROM bericht WHERE team_id = ? ORDER BY id ASC";
        List<Message> messages = new ArrayList<>();

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, team_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setText(resultSet.getString("bericht"));
                    message.setUserId(resultSet.getInt("user_id"));
                    message.setTeamId(resultSet.getInt("team_id"));

                    // Set itemId and itemType
                    if (resultSet.getObject("epic_id") != null) {
                        int epicId = resultSet.getInt("epic_id");
                        message.setItemId(epicId);
                        message.setItemType("epic");
                    } else if (resultSet.getObject("user_story_id") != null) {
                        int userStoryId = resultSet.getInt("user_story_id");
                        message.setItemId(userStoryId);
                        message.setItemType("user_story");
                    } else if (resultSet.getObject("taak_id") != null) {
                        int taakId = resultSet.getInt("taak_id");
                        message.setItemId(taakId);
                        message.setItemType("taak");
                    }

                    // Add the message to the list
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            System.err.println("Fout bij ophalen berichten: " + e.getMessage());
            throw new RuntimeException("Databasefout bij ophalen berichten", e);
        }


        return messages;
    }



    private String determineItemType(ResultSet resultSet) throws SQLException {
        if (resultSet.getObject("epic_id") != null) {
            return "epic";
        } else if (resultSet.getObject("user_story_id") != null) {
            return "user_story";
        } else if (resultSet.getObject("taak_id") != null) {
            return "taak";
        }
        return "unknown";
    }
}
