package com.example.psvm.model;

import com.example.psvm.database.WorkItemDB;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.psvm.database.WorkItemDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ScrumBoardTest {
    private WorkItemDB mockDB;
    private ScrumBoard scrumBoard;

    @BeforeEach
    void setUp() {
        mockDB = mock(WorkItemDB.class);
        scrumBoard = new ScrumBoard();
        scrumBoard.setWorkItemDB(mockDB); // ← Use a setter or constructor to inject the mock
    }

    @Test
    void testLoadWithValidTeamIdPopulatesMaps() {
        // Arrange
        Epic epic = new Epic(1, "Epic1", "Description");
        UserStory userStory = new UserStory(1, "UserStory1", "Description", 1);
        Taak taak = new Taak(1, "Task1", "Task Description", 1);

        when(mockDB.getAllEpics(1)).thenReturn(Collections.singletonList(epic));
        when(mockDB.getAllUserstories(1)).thenReturn(Collections.singletonList(userStory));
        when(mockDB.getAllTaken(1)).thenReturn(Collections.singletonList(taak));

        // Act
        scrumBoard.load(1);

        // Assert
        assertEquals(1, scrumBoard.epicMap.size());
        assertTrue(scrumBoard.epicMap.containsKey(1));
        assertEquals(epic, scrumBoard.epicMap.get(1));

        assertEquals(1, scrumBoard.userStoryMap.size());
        assertTrue(scrumBoard.userStoryMap.containsKey(1));
        assertEquals(userStory, scrumBoard.userStoryMap.get(1));

        assertEquals(1, scrumBoard.taakMap.size());
        assertTrue(scrumBoard.taakMap.containsKey(1));
        assertEquals(taak, scrumBoard.taakMap.get(1));

    }
}
