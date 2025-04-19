package com.example.psvm.database;

import com.example.psvm.model.Epic;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class WorkItemDBTest {

    /**
     * This class tests the getAllEpics method of the WorkItemDB class.
     * <p>
     * Method description:
     * The getAllEpics method retrieves all Epic objects for a specific team ID
     * from the database. It connects to the database, executes a query on the
     * epic table, and maps the result set to a list of Epic objects.
     * <p>
     * The method takes a single parameter:
     * - int teamID: the ID of the team whose epics should be fetched.
     * <p>
     * The returned result is a List of Epic objects.
     */

    @Test
    void testGetAllEpicsReturnsExpectedResults() throws SQLException {
        // Arrange
        int teamID = 1;
        List<Epic> expectedEpics = new ArrayList<>();
        expectedEpics.add(new Epic(1, "Epic 1", "Description of Epic 1"));
        expectedEpics.add(new Epic(2, "Epic 2", "Description of Epic 2"));

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("naam")).thenReturn("Epic 1", "Epic 2");
        when(mockResultSet.getString("beschrijving")).thenReturn("Description of Epic 1", "Description of Epic 2");

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        try (MockedStatic<ConnectionDB> mockedConnectionDB = Mockito.mockStatic(ConnectionDB.class)) {
            mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);

            WorkItemDB workItemDB = new WorkItemDB();

            // Act
            List<Epic> actualEpics = workItemDB.getAllEpics(teamID);

            // Assert
            assertEquals(expectedEpics.size(), actualEpics.size());
            for (int i = 0; i < expectedEpics.size(); i++) {
                assertEquals(expectedEpics.get(i).getId(), actualEpics.get(i).getId());
                assertEquals(expectedEpics.get(i).getNaam(), actualEpics.get(i).getNaam());
                assertEquals(expectedEpics.get(i).getBeschrijving(), actualEpics.get(i).getBeschrijving());
            }

            // Verify database interactions
            verify(mockPreparedStatement).setInt(1, teamID);
            verify(mockPreparedStatement).executeQuery();
            mockedConnectionDB.verify(ConnectionDB::getConnection, times(1));
        }
    }

    @Test
    void testGetAllEpicsHandlesEmptyResultSet() throws SQLException {
        // Arrange
        int teamID = 1;

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(false);

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        try (MockedStatic<ConnectionDB> mockedConnectionDB = Mockito.mockStatic(ConnectionDB.class)) {
            mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);

            WorkItemDB workItemDB = new WorkItemDB();

            // Act
            List<Epic> actualEpics = workItemDB.getAllEpics(teamID);

            // Assert
            assertTrue(actualEpics.isEmpty());

            // Verify database interactions
            verify(mockPreparedStatement).setInt(1, teamID);
            verify(mockPreparedStatement).executeQuery();
            mockedConnectionDB.verify(ConnectionDB::getConnection, times(1));
        }
    }

    @Test
    void testGetAllEpicsWithSQLException() throws SQLException {
        // Arrange
        int teamID = 1;

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Mock SQL Exception"));

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        try (MockedStatic<ConnectionDB> mockedConnectionDB = Mockito.mockStatic(ConnectionDB.class)) {
            mockedConnectionDB.when(ConnectionDB::getConnection).thenReturn(mockConnection);

            WorkItemDB workItemDB = new WorkItemDB();

            // Act
            List<Epic> actualEpics = workItemDB.getAllEpics(teamID);

            // Assert
            assertTrue(actualEpics.isEmpty());

            // Verify database interactions
            verify(mockPreparedStatement).setInt(1, teamID);
            verify(mockPreparedStatement).executeQuery();
            mockedConnectionDB.verify(ConnectionDB::getConnection, times(1));
        }
    }
}