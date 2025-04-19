package com.example.psvm.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ChatroomControllerTest {

    private ChatroomController controller;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        controller = new ChatroomController();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testErrorLabelNull() {
        String message = "Cannot invoke \"javafx.scene.control.Label.setText(String)\" because \"this.errorLabel\" is null";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            controller.showErrorMessage(message);
        });
        assertEquals(message, exception.getMessage());
    }
}