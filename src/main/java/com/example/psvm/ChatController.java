package com.example.psvm;

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

public class ChatController {

    @FXML
    private VBox chatMessages;
    @FXML
    private TextField chatInput;
    @FXML
    private Button sendButton;

    private Image profileImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/images/profile.png")).toExternalForm());

    @FXML
    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());
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
                ImageView profilePic = new ImageView(profileImage);
                profilePic.setFitHeight(30);
                profilePic.setFitWidth(30);
                messageBox.getChildren().add(profilePic);
                messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
                timeText.setStyle("-fx-font-size: 12px; -fx-fill: gray; -fx-alignment: CENTER_RIGHT;");
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
        // Hier kun je een logica toevoegen om te bepalen of het bericht van de huidige gebruiker is
        return true;
    }
}
