package com.example.psvm.controllers;

import com.example.psvm.model.SelectedItem;
import com.example.psvm.model.WorkItem;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrumboardController {

    @FXML
    private VBox epicsBox;

    @FXML
    private VBox userStoriesBox;

    @FXML
    private VBox takenBox;

    private final WorkItem workItem = new WorkItem();

    private ChatroomController chatroomController;

    public void setChatroomController(ChatroomController chatroomController) {
        this.chatroomController = chatroomController;
    }

    @FXML
    public void initialize() {
        loadEpics();
        loadUserStories();
        loadTaken();
    }

    private void loadEpics() {
        workItem.getAllEpics().forEach(epicNaam -> {
            Label label = createSelectableLabel(epicNaam, "epic");
            epicsBox.getChildren().add(label);
        });
    }

    private void loadUserStories() {
        workItem.getAllUserstories().forEach(storyNaam -> {
            Label label = createSelectableLabel(storyNaam, "user_story");
            userStoriesBox.getChildren().add(label);
        });
    }

    private void loadTaken() {
        workItem.workItemDB.getAllTaken().forEach(taakNaam -> {
            Label label = createSelectableLabel(taakNaam, "taak");
            takenBox.getChildren().add(label);
        });
    }

    private Label createSelectableLabel(String naam, String type) {
        Label label = new Label(naam);
        label.setStyle("-fx-cursor: hand;");

        label.setOnMouseClicked(event -> {
            Integer id = workItem.getWorkItemIdByName(naam, type);
            if (id != null && chatroomController != null) {
                chatroomController.setSelectedItem(new SelectedItem(id, type));
                Scene scene = label.getScene();
                if (scene != null) {
                    Stage stage = (Stage) scene.getWindow();
                    stage.close();
                }
            }
        });

        return label;
    }
}
