package com.example.psvm.database;

import com.example.psvm.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDB {
    public boolean sendMessage(int userId, String message, int team_id) {
        String query = "INSERT INTO bericht (user_id, bericht, team_id) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, team_id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Fout bij sturen van bericht: " + e.getMessage());
            throw new RuntimeException("Fout bij versturen van bericht");
        }
    }

    public List<Message> getMessages(int team_id) {
        String query = "SELECT id, bericht, user_id, team_id FROM bericht WHERE team_id = ? ORDER BY id ASC";
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
                    // Assuming parentId is optional, otherwise adjust logic to handle it
                    messages.add(message);
                }
            }

        } catch (SQLException e) {
            System.err.println("Fout bij ophalen berichten: " + e.getMessage());
            throw new RuntimeException("Databasefout bij ophalen berichten", e);
        }

        return messages;
    }
}
