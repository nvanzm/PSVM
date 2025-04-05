package com.example.psvm.controllers;

import com.example.psvm.model.Chat;
import com.example.psvm.util.errors.DomainException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.example.psvm.Startup.getChat;

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

    private Chat chat;

    @FXML
    public void initialize() {
        this.chat = getChat();  // Model instance
        sendButton.setOnAction(event -> sendMessage());

        mainHBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("mainHBox width: " + newValue);

            // Now bind chatContainer's width to 90% of the parent container's width
            chatBox.prefHeightProperty().bind(mainHBox.heightProperty());

            // Debugging: Print chatContainer's width whenever it changes
            chatBox.heightProperty().addListener((obs, oldVal, newVal) -> {
                System.out.println("chatContainer width: " + newVal);
            });
        });

        mainHBox.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ENTER) {
                sendMessage();
            }
        });
    }


    private void sendMessage() {
        String messageText = chatInput.getText();

        try {
            chat.sendChatMessage(messageText);
            displayMessage(messageText, true);
            chatInput.clear();
        } catch (DomainException e) {
            showErrorMessage(e.getMessage());
        } catch (Exception e) {
            showErrorMessage("Er is een fout opgetreden.");
        }
    }

    private void displayMessage(String messageText, boolean isCurrentUser) {
        Label messageLabel = new Label(messageText);
        messageLabel.getStyleClass().add("message-label");

        HBox messageBox = new HBox(10);
        messageBox.getChildren().add(messageLabel);

        if (isCurrentUser) {
            messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
        } else {
            messageBox.setStyle("-fx-alignment: CENTER_LEFT;");
        }

        chatMessages.getChildren().add(messageBox);
    }

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
    }
}
