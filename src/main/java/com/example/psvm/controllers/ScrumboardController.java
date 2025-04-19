package com.example.psvm.controllers;

import com.example.psvm.model.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.example.psvm.Startup.*;

/**
 * ScrumboardController manages the interaction between the UI components
 * and the underlying ScrumBoard data model. It is responsible for loading
 * and displaying epics, user stories, and tasks associated with a team,
 * and for handling user interactions with these work items.
 */
public class ScrumboardController {

    @FXML
    private VBox epicsBox;

    @FXML
    private VBox userStoriesBox;

    @FXML
    private VBox takenBox;

    private final ScrumBoard scrumBoard = getScrumBoard();
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
        scrumBoard.getAllEpics(team_id).forEach(epic -> {
            Label label = createSelectableLabel(epic.getNaam(), "epic");
            label.setUserData(epic);
            epicsBox.getChildren().add(label);
        });
    }

    private void loadUserStories() {
        scrumBoard.getAllUserstories(team_id).forEach(userStory -> {
            Label label = createSelectableLabel(userStory.getNaam(), "user_story");
            label.setUserData(userStory);
            userStoriesBox.getChildren().add(label);
        });
    }


    private void loadTaken() {
        scrumBoard.getAllTaken(team_id).forEach(taak -> {
            Label label = createSelectableLabel(taak.getNaam(), "taak");
            label.setUserData(taak);
            takenBox.getChildren().add(label);
        });
    }

    private Label createSelectableLabel(String naam, String type) {
        Label label = new Label(naam);
        label.setStyle("-fx-cursor: hand;");

        label.setOnMouseClicked(event -> {
            WorkItem obj = (WorkItem) label.getUserData();
            if (obj instanceof WorkItem item && chatroomController != null) {
                chatroomController.setSelectedItem(obj);
                Stage stage = (Stage) label.getScene().getWindow();
                if (stage != null) stage.close();
            }
        });

        return label;
    }

}
