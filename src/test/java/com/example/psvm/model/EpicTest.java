package com.example.psvm.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Epic} class.
 * <p>
 * The {@code Epic} class represents a type of WorkItem with unique properties such as id, name,
 * and description. The primary method being tested here is {@code getParentId()}, which currently
 * always returns 0.
 */
public class EpicTest {

    /**
     * Test to verify that the {@code getParentId()} method of the {@link Epic} class
     * returns 0 as expected.
     */
    @Test
    void testGetParentIdReturnsZero() {
        // Arrange
        int id = 1;
        String name = "Epic Name";
        String description = "Epic Description";
        Epic epic = new Epic(id, name, description);

        // Act
        int parentId = epic.getParentId();

        // Assert
        assertEquals(0, parentId, "The getParentId method should return 0.");
    }
}