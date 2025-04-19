package com.example.psvm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @Test
    void getTypeLabel_shouldReturnAlgemeen_whenItemTypeIsNull() {
        // Arrange
        Message message = new Message();
        message.setItemType(null);

        // Act
        String result = message.getTypeLabel();

        // Assert
        assertEquals("Algemeen", result);
    }

    @Test
    void getTypeLabel_shouldReturnEpic_whenItemTypeIsEpic() {
        // Arrange
        Message message = new Message();
        message.setItemType("epic");

        // Act
        String result = message.getTypeLabel();

        // Assert
        assertEquals("Epic", result);
    }

    @Test
    void getTypeLabel_shouldReturnUserStory_whenItemTypeIsUserStory() {
        // Arrange
        Message message = new Message();
        message.setItemType("user_story");

        // Act
        String result = message.getTypeLabel();

        // Assert
        assertEquals("User Story", result);
    }

    @Test
    void getTypeLabel_shouldReturnTaak_whenItemTypeIsTaak() {
        // Arrange
        Message message = new Message();
        message.setItemType("taak");

        // Act
        String result = message.getTypeLabel();

        // Assert
        assertEquals("Taak", result);
    }

    @Test
    void getTypeLabel_shouldReturnAlgemeen_whenItemTypeIsUnknown() {
        // Arrange
        Message message = new Message();
        message.setItemType("unknown");

        // Act
        String result = message.getTypeLabel();

        // Assert
        assertEquals("Algemeen", result);
    }

    @Test
    void getTypeLabel_shouldIgnoreCase_whenItemTypeIsMixedCase() {
        // Arrange
        Message message = new Message();
        message.setItemType("EpIc");

        // Act
        String result = message.getTypeLabel();

        // Assert
        assertEquals("Epic", result);
    }
}