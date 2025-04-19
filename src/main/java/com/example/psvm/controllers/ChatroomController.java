package com.example.psvm.controllers;

import com.example.psvm.model.*;
import com.example.psvm.util.errors.DomainException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;


import static com.example.psvm.Startup.getChat;
import static com.example.psvm.Startup.getUser;

/**
 * The ChatroomController class manages the functionality of the chatroom interface,
 * including sending and displaying chat messages, handling user interactions, and refreshing
 * the chat display.
 */
public class ChatroomController {

    @FXML
    VBox chatMessages;
    @FXML
    TextField chatInput;
    @FXML
    Button sendButton;
    @FXML
    HBox mainHBox;
    @FXML
    Label errorLabel;
    @FXML
    VBox chatBox;
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label teamChat;
    @FXML
    ComboBox<String> chatSelectie;

    private final ResolutionController resolutionManager = ResolutionController.getInstance();

    Chat chat;
    Team team;
    User user;
    List<Message> messages;
    int userId;
    int team_id;
    String team_name;
    private WorkItem selectedItem;
    Timeline refreshTimeline;

    @FXML
    public void initialize() {
        this.chat = getChat();
        this.user = getUser();
        this.userId = user.getId();

        this.team = new Team();
        this.team_id = user.getTeamIdById(userId);
        this.team_name = team.getTeamNameById(team_id);

        teamChat.setText("\uD83D\uDCAC Chat - " + team_name);

        System.out.println("Team ID: " + user.getTeamIdById(userId));

        applyResolution(resolutionManager.getCurrentResolution());

        sendButton.setOnAction(event -> sendMessage());
        chatBox.prefHeightProperty().bind(mainHBox.heightProperty());
        scrollPane.setVvalue(1.0);
        displayMessages();

        chatSelectie.setOnAction(e -> {
            String selected = chatSelectie.getValue();
            if ("Scrumboard".equals(selected)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/psvm/screens/scrumboard-modal.fxml"));
                    Parent root = loader.load();

                    ScrumboardController scrumboardController = loader.getController();
                    scrumboardController.setChatroomController(this);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if ("Algemene chat".equals(selected)) {
                setSelectedItem(null);
            }
        });

        mainHBox.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });

        // chatbox refresh
        refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> displayMessages())
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    public void setSelectedItem(WorkItem workItem) {
        this.selectedItem = workItem;
    }

    void applyResolution(String resolution) {
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
        Integer itemId = null;
        String itemType = null;

        if (selectedItem != null) {
            itemId = selectedItem.getId();
            itemType = selectedItem.getClass().getSimpleName();
            System.out.println("→ Geselecteerd item: ID=" + itemId + ", type=" + itemType);
        }

        try {
            chat.sendChatMessage(userId, messageText, team_id, itemId, itemType);
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
        this.messages = chat.getMessages(team_id);
        chatMessages.getChildren().clear();

        for (Message message : messages) {
            WorkItem parentItem = message.getWorkItem();

            if (selectedItem == null ) {
                String username = user.getNameById(message.getUserId());

                Label usernameLabel = new Label(username);
                usernameLabel.getStyleClass().add("username-label");
                usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333; -fx-font-size: 12px;");

                Label messageLabel = new Label(message.getText());
                messageLabel.getStyleClass().add("message-label");

                HBox typeIndicator = new HBox();
                Circle typeCircle = new Circle(10);
                Label typeLabel = new Label(message.getTypeLabel());

                typeCircle.getStyleClass().add(message.getStyleClassForType());

                typeCircle.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/psvm/screens/type-modal.fxml"));
                        Parent root = loader.load();

                        TypeController controller = loader.getController();
                        controller.setMessage(message);

                        Stage modalStage = new Stage();
                        modalStage.initModality(Modality.APPLICATION_MODAL);
                        modalStage.setScene(new Scene(root));
                        modalStage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                typeIndicator.getChildren().addAll(typeCircle, typeLabel);
                typeIndicator.setSpacing(5);

                HBox messageBox = new HBox(10);
                VBox messageContent = new VBox(3);
                messageContent.getChildren().addAll(usernameLabel, messageLabel);

                messageBox.getChildren().addAll(typeIndicator, messageContent);

                if (message.getUserId() == user.getId()) {
                    messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
                } else {
                    messageBox.setStyle("-fx-alignment: CENTER_LEFT;");
                }

                chatMessages.getChildren().add(messageBox);
                } else if ( parentItem!= null && parentItem.getId() == selectedItem.getId()) {
                String username = user.getNameById(message.getUserId());

                Label usernameLabel = new Label(username);
                usernameLabel.getStyleClass().add("username-label");
                usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333; -fx-font-size: 12px;");

                Label messageLabel = new Label(message.getText());
                messageLabel.getStyleClass().add("message-label");

                HBox typeIndicator = new HBox();
                Circle typeCircle = new Circle(10);
                Label typeLabel = new Label(message.getTypeLabel());

                typeCircle.getStyleClass().add(message.getStyleClassForType());

                typeCircle.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/psvm/screens/type-modal.fxml"));
                        Parent root = loader.load();

                        TypeController controller = loader.getController();
                        controller.setMessage(message);

                        Stage modalStage = new Stage();
                        modalStage.initModality(Modality.APPLICATION_MODAL);
                        modalStage.setScene(new Scene(root));
                        modalStage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                typeIndicator.getChildren().addAll(typeCircle, typeLabel);
                typeIndicator.setSpacing(5);

                HBox messageBox = new HBox(10);
                VBox messageContent = new VBox(3);
                messageContent.getChildren().addAll(usernameLabel, messageLabel);

                messageBox.getChildren().addAll(typeIndicator, messageContent);

                if (message.getUserId() == user.getId()) {
                    messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
                } else {
                    messageBox.setStyle("-fx-alignment: CENTER_LEFT;");
                }

                chatMessages.getChildren().add(messageBox);
            }
        }

        // Scroll to bottom after all messages are added
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }


    private void showErrorMessage(String message) {
        errorLabel.setText(message);
    }

    public ResolutionController getResolutionManager() {
        return resolutionManager;
    }

    protected Chat getChat() {
        return com.example.psvm.Startup.getChat();
    }

    protected User getUser() {
        return com.example.psvm.Startup.getUser();
    }
}
