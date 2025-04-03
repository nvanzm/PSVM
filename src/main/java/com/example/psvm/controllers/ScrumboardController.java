package com.example.psvm.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class ScrumboardController {

    @FXML
    private HBox mainHBox;

    private final ResolutionController resolutionManager = ResolutionController.getInstance();

    @FXML
    public void initialize() {
        applyResolution(resolutionManager.getCurrentResolution());

    }

    private void applyResolution(String resolution) {
        String[] dimensions = resolution.split("x");
        if (dimensions.length == 2) {
            try {
                double width = Double.parseDouble(dimensions[0]);
                double height = Double.parseDouble(dimensions[1]);
                mainHBox.setPrefWidth(width);
                mainHBox.setPrefHeight(height);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
