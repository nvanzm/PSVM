package com.example.psvm.model;

import com.example.psvm.database.TeamDB;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamTest {

    /**
     * Tests the createNewTeam method of the Team class.
     * The method attempts to add a new team to the database using the TeamDB object.
     * If successful, it updates the Team object's id and name, and returns true.
     * If the database operation fails, it logs a message and returns false.
     */

    @Test
    void createNewTeam_whenSuccessful_shouldReturnTrueAndSetIdAndName() {
        // Arrange
        String teamName = "Team Alpha";
        int generatedId = 42;

        TeamDB mockTeamDB = Mockito.mock(TeamDB.class);
        when(mockTeamDB.addNewTeamToDB(teamName)).thenReturn(Optional.of(generatedId));

        Team team = new Team();
        team.teamDB = mockTeamDB;

        // Act
        boolean result = team.createNewTeam(teamName);

        // Assert
        assertTrue(result);
        assertEquals(generatedId, team.getId());
        assertEquals(teamName, team.getName());
        verify(mockTeamDB, times(1)).addNewTeamToDB(teamName);
    }

    @Test
    void createNewTeam_whenFails_shouldReturnFalseAndNotSetIdOrName() {
        // Arrange
        String teamName = "Team Beta";

        TeamDB mockTeamDB = Mockito.mock(TeamDB.class);
        when(mockTeamDB.addNewTeamToDB(teamName)).thenReturn(Optional.empty());

        Team team = new Team();
        team.teamDB = mockTeamDB;

        // Act
        boolean result = team.createNewTeam(teamName);

        // Assert
        assertFalse(result);
        assertEquals(0, team.getId());
        assertNull(team.getName());
        verify(mockTeamDB, times(1)).addNewTeamToDB(teamName);
    }
}