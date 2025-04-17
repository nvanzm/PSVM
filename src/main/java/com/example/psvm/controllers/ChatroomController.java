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
    @FXML
    private Label teamChat;
    @FXML
    private ComboBox<String> chatSelectie;

    private final ResolutionController resolutionManager = ResolutionController.getInstance();

    private Chat chat;
    private Team team;
    private User user;
    private List<Message> messages;
    private int userId;
    private int team_id;
    private String team_name;
    private SelectedItem selectedItem;

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

                    chatSelectie.setValue("Algemene chat");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        mainHBox.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });

        // chatbox refresh
        Timeline refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> displayMessages())
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    public void setSelectedItem(SelectedItem selectedItem) {
        this.selectedItem = selectedItem;
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
        Integer itemId = null;
        String itemType = null;

        if (selectedItem != null) {
            itemId = selectedItem.getId();
            itemType = selectedItem.getType();
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
            messageContent.getChildren().add(usernameLabel);
            messageContent.getChildren().add(messageLabel);

            messageBox.getChildren().add(typeIndicator);
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
