package com.example.psvm.database;

import com.example.psvm.model.Message;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatDBTest {

    /**
     * Tests for the sendMessage method in the ChatDB class.
     * The sendMessage method is responsible for inserting a message into the database for a
     * specific user, team, and optional associated item (Epic, UserStory, or Task).
     */

    @Test
    void sendMessage_SuccessForEpic() throws SQLException {
        // Arrange
        int userId = 1;
        String message = "Test epic message";
        int teamId = 2;
        int itemId = 10;
        String itemType = "Epic";

        MockedStatic<ConnectionDB> mockedConnectionDB = mockStatic(ConnectionDB.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        ChatDB chatDB = new ChatDB();

        // Act
        boolean result = chatDB.sendMessage(userId, message, teamId, itemId, itemType);

        // Assert
        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).setString(2, message);
        verify(mockPreparedStatement).setInt(3, teamId);
        verify(mockPreparedStatement).setInt(4, itemId);
        verify(mockPreparedStatement).setNull(5, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).setNull(6, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).executeUpdate();

        mockedConnectionDB.close();
    }

    @Test
    void sendMessage_SuccessForUserStory() throws SQLException {
        // Arrange
        int userId = 1;
        String message = "Test user story message";
        int teamId = 2;
        int itemId = 20;
        String itemType = "UserStory";

        MockedStatic<ConnectionDB> mockedConnectionDB = mockStatic(ConnectionDB.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        ChatDB chatDB = new ChatDB();

        // Act
        boolean result = chatDB.sendMessage(userId, message, teamId, itemId, itemType);

        // Assert
        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).setString(2, message);
        verify(mockPreparedStatement).setInt(3, teamId);
        verify(mockPreparedStatement).setNull(4, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).setInt(5, itemId);
        verify(mockPreparedStatement).setNull(6, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).executeUpdate();

        mockedConnectionDB.close();
    }

    @Test
    void sendMessage_SuccessForTask() throws SQLException {
        // Arrange
        int userId = 1;
        String message = "Test task message";
        int teamId = 2;
        int itemId = 30;
        String itemType = "Taak";

        MockedStatic<ConnectionDB> mockedConnectionDB = mockStatic(ConnectionDB.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        ChatDB chatDB = new ChatDB();

        // Act
        boolean result = chatDB.sendMessage(userId, message, teamId, itemId, itemType);

        // Assert
        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).setString(2, message);
        verify(mockPreparedStatement).setInt(3, teamId);
        verify(mockPreparedStatement).setNull(4, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).setNull(5, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).setInt(6, itemId);
        verify(mockPreparedStatement).executeUpdate();

        mockedConnectionDB.close();
    }

    @Test
    void sendMessage_SuccessForNoItemType() throws SQLException {
        // Arrange
        int userId = 1;
        String message = "Test message without item type";
        int teamId = 2;
        Integer itemId = null;
        String itemType = null;

        MockedStatic<ConnectionDB> mockedConnectionDB = mockStatic(ConnectionDB.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        ChatDB chatDB = new ChatDB();

        // Act
        boolean result = chatDB.sendMessage(userId, message, teamId, itemId, itemType);

        // Assert
        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).setString(2, message);
        verify(mockPreparedStatement).setInt(3, teamId);
        verify(mockPreparedStatement).setNull(4, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).setNull(5, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).setNull(6, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).executeUpdate();

        mockedConnectionDB.close();
    }

    @Test
    void sendMessage_FailureDueToSQLException() throws SQLException {
        // Arrange
        int userId = 1;
        String message = "Test failure message";
        int teamId = 2;
        Integer itemId = null;
        String itemType = null;

        MockedStatic<ConnectionDB> mockedConnectionDB = mockStatic(ConnectionDB.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        ChatDB chatDB = new ChatDB();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                chatDB.sendMessage(userId, message, teamId, itemId, itemType)
        );
        assertEquals("Fout bij versturen van bericht", exception.getMessage());

        mockedConnectionDB.close();
    }
}