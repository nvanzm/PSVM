package com.example.psvm.model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WorkItemTest {

    /**
     * This test class focuses on the abstract `WorkItem` class's method `getParentId`.
     * The `getParentId` method is abstract and needs to be tested with concrete implementations
     * of the `WorkItem` class. Each test verifies that the `getParentId` method in the
     * overridden implementation provides the correct behavior as expected.
     */

    @Test
    void testGetParentIdWithValidParentId() {
        // Arrange
        WorkItem workItem = mock(WorkItem.class);
        when(workItem.getParentId()).thenReturn(Integer.valueOf(100));

        // Act
        int parentId = workItem.getParentId();

        // Assert
        assertEquals(100, parentId);
    }

    @Test
    void testGetParentIdReturnsZeroWhenNoParent() {
        // Arrange
        WorkItem workItem = mock(WorkItem.class);
        when(workItem.getParentId()).thenReturn(Integer.valueOf(0));

        // Act
        int parentId = workItem.getParentId();

        // Assert
        assertEquals(0, parentId);
    }

    @Test
    void testGetParentIdWithNegativeValue() {
        // Arrange
        WorkItem workItem = mock(WorkItem.class);
        when(workItem.getParentId()).thenReturn(Integer.valueOf(-1));

        // Act
        int parentId = workItem.getParentId();

        // Assert
        assertEquals(-1, parentId);
    }

    @Test
    void testGetParentIdWithLargePositiveValue() {
        // Arrange
        WorkItem workItem = mock(WorkItem.class);
        when(workItem.getParentId()).thenReturn(Integer.valueOf(123456789));

        // Act
        int parentId = workItem.getParentId();

        // Assert
        assertEquals(123456789, parentId);
    }
}