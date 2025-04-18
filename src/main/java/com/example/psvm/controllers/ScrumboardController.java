package com.example.psvm.controllers;

import com.example.psvm.model.SelectedItem;
import com.example.psvm.model.Team;
import com.example.psvm.model.User;
import com.example.psvm.model.ScrumBoard;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.example.psvm.Startup.*;

public class ScrumboardController {

    @FXML
    private VBox epicsBox;

    @FXML
    private VBox userStoriesBox;

    @FXML
    private VBox takenBox;

    private final ScrumBoard workItem = getScrumBoard();
    private Team team;
    private int team_id;
    private User user;

    private ChatroomController chatroomController;

    public void setChatroomController(ChatroomController chatroomController) {
        this.chatroomController = chatroomController;
    }

    @FXML
    public void initialize() {
        this.team = getTeam();
        this.user = getUser();
        this.team_id = user.getTeamIdById(user.getId());
        loadEpics();
        loadUserStories();
        loadTaken();
    }

    private void loadEpics() {
        workItem.getAllEpics(team_id).forEach(epic -> {
            Label label = createSelectableLabel(epic.getNaam(), "epic");
            epicsBox.getChildren().add(label);
        });
    }

    private void loadUserStories() {
        workItem.getAllUserstories(team_id).forEach(userStory -> {
            Label label = createSelectableLabel(userStory.getNaam(), "user_story");
            userStoriesBox.getChildren().add(label);
        });
    }

    private void loadTaken() {
        workItem.getAllTaken(team_id).forEach(taak -> {
            Label label = createSelectableLabel(taak.getNaam(), "taak");
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
