package com.example.psvm.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ScrumboardControllerTest {

    private ScrumboardController controller;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        controller = new ScrumboardController();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testErrorThisUserIsNull() {
        String message = "Cannot invoke \"com.example.psvm.model.User.getId()\" because \"this.user\" is null";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
        assertEquals(message, exception.getMessage());
    }
}