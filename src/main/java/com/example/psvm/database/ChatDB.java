package com.example.psvm.database;

import com.example.psvm.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDB {

    public boolean sendMessage(int userId, String message, int team_id, Integer itemId, String itemType) {
        String query = "INSERT INTO bericht (user_id, bericht, team_id, epic_id, user_story_id, taak_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, team_id);

            if ("epic".equals(itemType)) {
                preparedStatement.setInt(4, itemId);
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            } else if ("user_story".equals(itemType)) {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
                preparedStatement.setInt(5, itemId);
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            } else if ("taak".equals(itemType)) {
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
                    message.setItemId(resultSet.getObject("epic_id") != null ? resultSet.getInt("epic_id") :
                            (resultSet.getObject("user_story_id") != null ? resultSet.getInt("user_story_id") :
                                    resultSet.getInt("taak_id")));
                    message.setItemType(determineItemType(resultSet));
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
