package com.example.psvm.controllers;

import com.example.psvm.database.ChatDB;
import com.example.psvm.database.UserDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
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

    private Chat chat;
    private final ResolutionController resolutionManager = ResolutionController.getInstance();

    @FXML
    public void initialize() {
        chat = new Chat(chatMessages);
        sendButton.setOnAction(event -> sendMessage());
        applyResolution(resolutionManager.getCurrentResolution());

        UserDB userDB = new UserDB();
        List<String> users = userDB.getAllUserNames();

        if (users.isEmpty()) {
            System.out.println("Geen gebruikers gevonden.");
        } else {
            System.out.println(users);
        }
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
        ChatDB chatDB = new ChatDB();

        String messageText = chatInput.getText();
        chat.sendMessage(messageText, isCurrentUserMessage());

        boolean success = chatDB.sendMessage(messageText);

        if (success) {
            System.out.println("Bericht succesvol opgeslagen in de database.");
        } else {
            System.out.println("Fout bij opslaan van bericht.");
        }

        chatInput.clear();
    }

    private boolean isCurrentUserMessage() {
        return true;
    }

    private static class Chat {
        private final VBox chatMessages;
        private final Image profileImage;

        public Chat(VBox chatMessages) {
            this.chatMessages = chatMessages;
            this.profileImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/images/profile.png")).toExternalForm());
        }

        public void sendMessage(String messageText, boolean isCurrentUser) {
            if (messageText == null || messageText.trim().isEmpty()) {
                return;
            }

            Label messageLabel = new Label(messageText);
            messageLabel.getStyleClass().add("message-label");

            HBox messageBox = new HBox(10);
            messageBox.getChildren().add(messageLabel);

            if (isCurrentUser) {
                ImageView profilePic = new ImageView(profileImage);
                profilePic.setFitHeight(30);
                profilePic.setFitWidth(30);
                messageBox.getChildren().add(profilePic);
                messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
            } else {
                messageBox.setStyle("-fx-alignment: CENTER_LEFT;");
            }

            VBox fullMessage = new VBox(5);
            fullMessage.getChildren().addAll(messageBox);

            chatMessages.getChildren().add(fullMessage);
        }
    }
}
