package com.example.psvm.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TeamsControllerTest {

    private TeamsController controller;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        controller = new TeamsController();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testErrorPlusButtonIsNull() {
        String message = "Cannot invoke \"javafx.scene.control.Button.setOnAction(javafx.event.EventHandler)\" because \"this.plusButton\" is null";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
        assertEquals(message, exception.getMessage());
    }
}