package com.example.psvm.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WorkItemControllerTest {

    private WorkitemController controller;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        controller = new WorkitemController();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testErrorWorkItemPlusButtonIsNull() {
        String message = "Cannot invoke \"javafx.scene.control.Button.setOnAction(javafx.event.EventHandler)\" because \"this.WorkItemPlusButton\" is null";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
        assertEquals(message, exception.getMessage());
    }
}