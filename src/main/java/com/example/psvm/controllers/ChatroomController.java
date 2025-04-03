package com.example.psvm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ChatroomController {

    @FXML
    private VBox chatMessages;
    @FXML
    private TextField chatInput;
    @FXML
    private Button sendButton;
    @FXML
    private HBox mainHBox;

    private final Image profileImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/images/profile.png")).toExternalForm());
    private final ResolutionController resolutionManager = ResolutionController.getInstance();

    @FXML
    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());
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

    private void sendMessage() {
        String messageText = chatInput.getText();

        if (messageText != null && !messageText.trim().isEmpty()) {
            Label messageLabel = new Label(messageText);
            messageLabel.getStyleClass().add("message-label");

            Text timeText = new Text(getCurrentTime());
            timeText.setStyle("-fx-font-size: 12px; -fx-fill: gray;");

            HBox messageBox = new HBox(10);
            messageBox.getChildren().add(messageLabel);

            if (isCurrentUserMessage()) {
//                ImageView profilePic = new ImageView(profileImage);
//                profilePic.setFitHeight(30);
//                profilePic.setFitWidth(30);
//                messageBox.getChildren().add(profilePic);
//                messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
//                timeText.setStyle("-fx-alignment: CENTER_RIGHT !important; -fx-font-size: 12px; -fx-fill: gray; ");

                messageBox.getStyleClass().add("chat-message-right");
                messageBox.getStyleClass().add("time-text-right");
            } else {
                messageBox.setStyle("-fx-alignment: CENTER_LEFT;");
                timeText.setStyle("-fx-font-size: 12px; -fx-fill: gray; -fx-alignment: CENTER_LEFT;");
            }

            VBox fullMessage = new VBox(5);
            fullMessage.getChildren().addAll(messageBox, timeText);

            chatMessages.getChildren().add(fullMessage);
            chatInput.clear();
        }
    }

    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.now().format(formatter);
    }

    private boolean isCurrentUserMessage() {
        return true;
    }
}
