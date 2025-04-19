package com.example.psvm.database;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LoginDBTest {

    /**
     * Tests for the `getUserIdByUsername` method in the `LoginDB` class.
     * <p>
     * This method retrieves the user ID from the database based on the provided username.
     * It returns an `Optional<Integer>`, where the value is present if the username exists in the database.
     */

    @Test
    void testGetUserIdByUsername_UserExists() throws SQLException {
        // Arrange
        String testUsername = "existingUser";
        int mockUserId = 42;

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement("SELECT id FROM user WHERE naam = ?")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(mockUserId);

        Mockito.mockStatic(ConnectionDB.class).when(ConnectionDB::getConnection).thenReturn(mockConnection);

        LoginDB loginDB = new LoginDB();

        // Act
        Optional<Integer> result = loginDB.getUserIdByUsername(testUsername);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockUserId, result.get());

        Mockito.verify(mockPreparedStatement).setString(1, testUsername);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockResultSet).next();
        Mockito.verify(mockResultSet).getInt("id");

        Mockito.clearAllCaches();
    }

    @Test
    void testGetUserIdByUsername_UserDoesNotExist() throws SQLException {
        // Arrange
        String testUsername = "nonExistingUser";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement("SELECT id FROM user WHERE naam = ?")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Mockito.mockStatic(ConnectionDB.class).when(ConnectionDB::getConnection).thenReturn(mockConnection);

        LoginDB loginDB = new LoginDB();

        // Act
        Optional<Integer> result = loginDB.getUserIdByUsername(testUsername);

        // Assert
        assertTrue(result.isEmpty());

        Mockito.verify(mockPreparedStatement).setString(1, testUsername);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockResultSet).next();

        Mockito.clearAllCaches();
    }

    @Test
    void testGetUserIdByUsername_SQLException() throws SQLException {
        // Arrange
        String testUsername = "anyUser";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement("SELECT id FROM user WHERE naam = ?")).thenReturn(mockPreparedStatement);
        doThrow(new SQLException("Simulated SQL exception")).when(mockPreparedStatement).executeQuery();

        Mockito.mockStatic(ConnectionDB.class).when(ConnectionDB::getConnection).thenReturn(mockConnection);

        LoginDB loginDB = new LoginDB();

        // Act & Assert
        RuntimeException exception = null;
        try {
            loginDB.getUserIdByUsername(testUsername);
        } catch (RuntimeException e) {
            exception = e;
        }

        assertTrue(exception instanceof RuntimeException);
        assertEquals("Databasefout bij ophalen van gebruiker ID", exception.getMessage());

        Mockito.verify(mockPreparedStatement).setString(1, testUsername);
        Mockito.verify(mockPreparedStatement).executeQuery();

        Mockito.clearAllCaches();
    }
}