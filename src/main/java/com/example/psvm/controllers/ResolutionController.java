package com.example.psvm.controllers;

import java.util.prefs.Preferences;

public class ResolutionController {
    private static final ResolutionController INSTANCE = new ResolutionController();
    private final Preferences prefs;
    private String currentResolution;

    private ResolutionController() {
        prefs = Preferences.userNodeForPackage(ResolutionController.class);
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

