package com.example.psvm.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController controller;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testErrorLabelNull() {
        String message = "Cannot invoke \"javafx.scene.control.Label.setStyle(String)\" because \"this.errorLabel\" is null";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            controller.showError(message);
        });
        assertEquals(message, exception.getMessage());
    }
}