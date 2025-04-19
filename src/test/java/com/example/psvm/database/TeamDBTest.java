package com.example.psvm.database;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeamDBTest {

    @Test
    public void testAddNewTeamToDB_SuccessfulInsert() throws Exception {
        String teamNaam = "Team A";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<ConnectionDB> mockedStatic = mockStatic(ConnectionDB.class)) {
            mockedStatic.when(ConnectionDB::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(100);

            TeamDB teamDB = new TeamDB();

            Optional<Integer> result = teamDB.addNewTeamToDB(teamNaam);

            assertTrue(result.isPresent());
            assertEquals(100, result.get());
            verify(mockPreparedStatement).setString(1, teamNaam);
            verify(mockPreparedStatement).executeUpdate();
            verify(mockResultSet).next();
            verify(mockResultSet).getInt(1);
        }
    }

    @Test
    public void testAddNewTeamToDB_NoRowsInserted() throws Exception {
        String teamNaam = "Team B";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        try (MockedStatic<ConnectionDB> mockedStatic = mockStatic(ConnectionDB.class)) {
            mockedStatic.when(ConnectionDB::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            TeamDB teamDB = new TeamDB();

            Optional<Integer> result = teamDB.addNewTeamToDB(teamNaam);

            assertFalse(result.isPresent());
            verify(mockPreparedStatement).setString(1, teamNaam);
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    public void testAddNewTeamToDB_NoGeneratedKeys() throws Exception {
        String teamNaam = "Team C";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<ConnectionDB> mockedStatic = mockStatic(ConnectionDB.class)) {
            mockedStatic.when(ConnectionDB::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            TeamDB teamDB = new TeamDB();

            Optional<Integer> result = teamDB.addNewTeamToDB(teamNaam);

            assertFalse(result.isPresent());
            verify(mockPreparedStatement).setString(1, teamNaam);
            verify(mockPreparedStatement).executeUpdate();
            verify(mockResultSet).next();
        }
    }

    @Test
    public void testAddNewTeamToDB_ThrowsSQLException() throws Exception {
        String teamNaam = "Team D";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        try (MockedStatic<ConnectionDB> mockedStatic = mockStatic(ConnectionDB.class)) {
            mockedStatic.when(ConnectionDB::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

            TeamDB teamDB = new TeamDB();

            RuntimeException exception = assertThrows(RuntimeException.class, () -> teamDB.addNewTeamToDB(teamNaam));
            assertEquals("Databasefout bij toevoegen team: ", exception.getMessage());
            verify(mockPreparedStatement).setString(1, teamNaam);
            verify(mockPreparedStatement).executeUpdate();
        }
    }
}