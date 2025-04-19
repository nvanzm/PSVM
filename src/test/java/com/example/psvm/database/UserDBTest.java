package com.example.psvm.database;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDBTest {

    @Test
    void testGetIDFromTeamName_Found() throws SQLException {
        String teamName = "TeamA";
        int expectedID = 1;

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<ConnectionDB> mockedStatic = mockStatic(ConnectionDB.class)) {
            mockedStatic.when(ConnectionDB::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(expectedID);

            UserDB userDB = new UserDB();

            Optional<Integer> result = userDB.getIDFromTeamName(teamName);

            assertTrue(result.isPresent());
            assertEquals(expectedID, result.get());

            verify(mockPreparedStatement).setString(1, teamName);
            verify(mockPreparedStatement).executeQuery();
        }
    }

    @Test
    void testGetIDFromTeamName_NotFound() throws SQLException {
        String teamName = "NonExistentTeam";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<ConnectionDB> mockedStatic = mockStatic(ConnectionDB.class)) {
            mockedStatic.when(ConnectionDB::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            UserDB userDB = new UserDB();

            Optional<Integer> result = userDB.getIDFromTeamName(teamName);

            assertFalse(result.isPresent());

            verify(mockPreparedStatement).setString(1, teamName);
            verify(mockPreparedStatement).executeQuery();
        }
    }

    @Test
    void testGetIDFromTeamName_SQLException() throws SQLException {
        String teamName = "TeamA";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        try (MockedStatic<ConnectionDB> mockedStatic = mockStatic(ConnectionDB.class)) {
            mockedStatic.when(ConnectionDB::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

            UserDB userDB = new UserDB();

            RuntimeException exception = assertThrows(RuntimeException.class, () -> userDB.getIDFromTeamName(teamName));
            assertEquals("Databasefout bij ophalen teamID: ", exception.getMessage());

            verify(mockPreparedStatement).setString(1, teamName);
            verify(mockPreparedStatement).executeQuery();
        }
    }
}