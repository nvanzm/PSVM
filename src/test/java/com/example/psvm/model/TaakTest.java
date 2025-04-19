package com.example.psvm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaakTest {

    @Test
    void testGetParentId_ReturnsCorrectParentId() {
        // Arrange
        int expectedId = 42;
        Taak taak = new Taak(1, "Test Naam", "Test Beschrijving", expectedId);

        // Act
        int actualId = taak.getParentId();

        // Assert
        assertEquals(expectedId, actualId, "The getParentId method should return the correct parent ID.");
    }

    @Test
    void testGetParentId_ReturnsZeroForDefaultConstructorValues() {
        // Arrange
        int expectedId = 0;
        Taak taak = new Taak(1, "Test Naam", "Test Beschrijving", expectedId);

        // Act
        int actualId = taak.getParentId();

        // Assert
        assertEquals(expectedId, actualId, "The getParentId method should return 0 when initialized with 0.");
    }

    @Test
    void testGetParentId_WithNegativeValue() {
        // Arrange
        int expectedId = -1;
        Taak taak = new Taak(1, "Test Naam", "Test Beschrijving", expectedId);

        // Act
        int actualId = taak.getParentId();

        // Assert
        assertEquals(expectedId, actualId, "The getParentId method should return the negative value when assigned.");
    }
}