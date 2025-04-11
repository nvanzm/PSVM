package com.example.psvm.controllers;

import com.example.psvm.model.Chat;
import com.example.psvm.model.Message;
import com.example.psvm.model.User;
import com.example.psvm.util.errors.DomainException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

import static com.example.psvm.Startup.getChat;
import static com.example.psvm.Startup.getUser;

public class ChatroomController {
    @FXML
    private VBox chatMessages;
    @FXML
    private TextField chatInput;
    @FXML
    private Button sendButton;
    @FXML
    private HBox mainHBox;
    @FXML
    private Label errorLabel;
    @FXML
    private VBox chatBox;
    @FXML
    private ScrollPane scrollPane;

    private final ResolutionController resolutionManager = ResolutionController.getInstance();

    private Chat chat;
    private User user;
    private List<Message> messages;
    private int userId;
    private int team_id;

    @FXML
    public void initialize() {
        this.chat = getChat();  // Model instance
        this.user = getUser();
        this.userId = user.getId();
        this.team_id = user.getTeamIdById(userId);

        System.out.println("Team ID: " + user.getTeamIdById(userId));

        applyResolution(resolutionManager.getCurrentResolution());

        sendButton.setOnAction(event -> sendMessage());
        chatBox.prefHeightProperty().bind(mainHBox.heightProperty());
        scrollPane.setVvalue(1.0);
        displayMessages();

        mainHBox.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ENTER) {
                sendMessage();
            }
        });

        //chatbox refresh
        Timeline refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> displayMessages())
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
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

        try {
            chat.sendChatMessage(userId, messageText, team_id);
            displayMessages();
            chatInput.clear();
        } catch (DomainException e) {
            showErrorMessage(e.getMessage());
        } catch (Exception e) {
            showErrorMessage("Er is een fout opgetreden.");
        }
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }

    private void displayMessages() {
        this.messages = chat.getMessages();

        chatMessages.getChildren().clear();

        // Loop through the messages and display each one
        for (Message message : messages) {
            String username = user.getNameById(message.getUserId());
            int team_id = user.getTeamIdById(message.getTeamId());

            Label usernameLabel = new Label(username);
            usernameLabel.getStyleClass().add("username-label");
            usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333; -fx-font-size: 12px;");

            Label messageLabel = new Label(message.getText());  // Use message.getText() for the actual message text
            messageLabel.getStyleClass().add("message-label");

            HBox messageBox = new HBox(10);

            VBox messageContent = new VBox(3);
            messageContent.getChildren().add(usernameLabel);
            messageContent.getChildren().add(messageLabel);

            messageBox.getChildren().add(messageContent);
            if (message.getUserId() == user.getId()) {
                messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
            } else {
                messageBox.setStyle("-fx-alignment: CENTER_LEFT;");
                Platform.runLater(() -> scrollPane.setVvalue(1.0));
            }
            chatMessages.getChildren().add(messageBox);
        }
    }

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
    }
}
