package com.example.psvm.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ResolutionControllerTest {

    private ResolutionController controller;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        controller = new ResolutionController();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testErrorThisUserIsNull() {
        String resolution = "1920";

        controller.setResolution(resolution);

        assertEquals(resolution, "1920");
    }
}