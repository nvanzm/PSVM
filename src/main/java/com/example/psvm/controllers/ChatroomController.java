package com.example.psvm.controllers;

import com.example.psvm.model.Chat;
import com.example.psvm.model.Message;
import com.example.psvm.model.User;
import com.example.psvm.util.errors.DomainException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    private Chat chat;
    private User user;
    private List<Message> messages;
    private int userId;
    @FXML
    public void initialize() {
        this.chat = getChat();  // Model instance
        this.user = getUser();
        this.userId = user.getId();

        sendButton.setOnAction(event -> sendMessage());
        chatBox.prefHeightProperty().bind(mainHBox.heightProperty());
        scrollPane.setVvalue(1.0);
        displayMessages();
        mainHBox.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ENTER) {
                sendMessage();
            }
        });


    }


    private void sendMessage() {
        String messageText = chatInput.getText();

        try {
            chat.sendChatMessage(userId, messageText);
            displayMessages();
            chatInput.clear();
        } catch (DomainException e) {
            showErrorMessage(e.getMessage());
        } catch (Exception e) {
            showErrorMessage("Er is een fout opgetreden.");
        }
    }

    private void displayMessages() {
        this.messages = chat.getMessages();

        chatMessages.getChildren().clear();

        // Loop through the messages and display each one
        for (Message message : messages) {
            String username = user.getNameById(message.getUserId());

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
            }

            chatMessages.getChildren().add(messageBox);
        }
    }

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
    }
}
