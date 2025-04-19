package com.example.psvm.controllers;

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

    private ResolutionController() {
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
        currentResolution = resolution;
        prefs.put("resolution", resolution);
    }
}

