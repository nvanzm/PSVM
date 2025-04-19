package com.example.psvm.controllers;

import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * The ResolutionController class is responsible for managing and persisting
 * screen resolution settings for the application. This includes retrieving the
 * current resolution and updating it as needed.
 */
public class ResolutionController {
    private static final ResolutionController INSTANCE = new ResolutionController();
    private final Preferences prefs;
    private String currentResolution;

    ResolutionController() {
        prefs = Preferences.userNodeForPackage(ResolutionController.class);
        currentResolution = prefs.get("resolution", "1920x1080");
    }

    public static ResolutionController getInstance() {
        return INSTANCE;
    }

    public String getCurrentResolution() {
        return currentResolution;
    }

    public void setResolution(String resolution) {
        if (Objects.equals(resolution, "1920x1080") || Objects.equals(resolution, "1550x880")) {
            currentResolution = resolution;
            prefs.put("resolution", resolution);
        } else {
            System.err.printf("Geen geldige resolutie.");
        }
    }
}

