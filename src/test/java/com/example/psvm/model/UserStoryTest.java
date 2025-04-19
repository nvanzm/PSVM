package com.example.psvm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserStoryTest {

    /**
     * Tests for the UserStory class.
     * <p>
     * This class verifies the behavior of the getParentId() method, which is responsible
     * for returning the epicId associated with the UserStory. Each test case ensures that
     * the returned epicId matches the expected value based on the UserStory instance.
     */

    @Test
    void testGetParentId_ReturnsCorrectEpicId() {
        // Arrange
        int expectedId = 42;
        UserStory userStory = new UserStory(1, "UserStoryName", "Description", expectedId);

        // Act
        int actualId = userStory.getParentId();

        // Assert
        assertEquals(expectedId, actualId, "The epicId should match the expected id.");
    }

    @Test
    void testGetParentId_ReturnsZeroWhenEpicIdIsZero() {
        // Arrange
        int expectedId = 0;
        UserStory userStory = new UserStory(2, "AnotherStory", "AnotherDescription", expectedId);

        // Act
        int actualId = userStory.getParentId();

        // Assert
        assertEquals(expectedId, actualId, "The epicId should be zero when initialized as zero.");
    }

    @Test
    void testGetParentId_WithNegativeEpicId() {
        // Arrange
        int expectedId = -5;
        UserStory userStory = new UserStory(3, "NegativeEpicIdStory", "NegativeEpicTest", expectedId);

        // Act
        int actualId = userStory.getParentId();

        // Assert
        assertEquals(expectedId, actualId, "The epicId should match the negative value provided.");
    }
}