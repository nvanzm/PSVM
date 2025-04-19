package com.example.psvm.model;

import com.example.psvm.database.UserDB;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    @Test
    void joinTeam_SuccessfulJoin() {
        // Arrange
        String chosenTeam = "Team A";
        int userId = 1;

        UserDB mockedUserDB = mock(UserDB.class);
        when(mockedUserDB.getIDFromTeamName(chosenTeam)).thenReturn(Optional.of(100));
        when(mockedUserDB.addUserToTeam(userId, 100)).thenReturn(Boolean.valueOf(true));

        User user = new User();
        user.userDB = mockedUserDB;

        // Act
        boolean result = user.joinTeam(chosenTeam, userId);

        // Assert
        assertTrue(result);
        assertEquals(chosenTeam, user.getTeam());
        verify(mockedUserDB).getIDFromTeamName(chosenTeam);
        verify(mockedUserDB).addUserToTeam(userId, 100);
    }

    @Test
    void joinTeam_AddUserToTeamFails() {
        // Arrange
        String chosenTeam = "Team C";
        int userId = 3;

        UserDB mockedUserDB = mock(UserDB.class);
        when(mockedUserDB.getIDFromTeamName(chosenTeam)).thenReturn(Optional.of(200));
        when(mockedUserDB.addUserToTeam(userId, 200)).thenReturn(Boolean.valueOf(false));

        User user = new User();
        user.userDB = mockedUserDB;

        // Act
        boolean result = user.joinTeam(chosenTeam, userId);

        // Assert
        assertFalse(result);
        assertEquals(chosenTeam, user.getTeam());
        verify(mockedUserDB).getIDFromTeamName(chosenTeam);
        verify(mockedUserDB).addUserToTeam(userId, 200);
    }
}