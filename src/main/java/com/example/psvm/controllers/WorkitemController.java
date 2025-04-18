package com.example.psvm.controllers;

import com.example.psvm.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.example.psvm.Startup.*;

public class WorkitemController {
    @FXML
    private Button WorkItemPlusButton;
    @FXML
    private TextField workitemNameField;
    @FXML
    private TextField workitemBeschrijving;
    @FXML
    private ComboBox typeSelectie;
    @FXML
    private ComboBox koppelSelectie;
    @FXML
    private Label koppel;
    @FXML
    private Label toegevoegd;


    private final ResolutionController resolutionManager = ResolutionController.getInstance();
    private UserStory userStory;
    private Epic epic;
    private Team team;
    private int team_id;
    private User user;
    private ScrumBoard scrumBoard;

    @FXML
    public void initialize() {
        WorkItemPlusButton.setOnAction(event -> {addWorkitem();});
        this.team = getTeam();
        this.user = getUser();
        this.scrumBoard = getScrumBoard();
        this.team_id = user.getTeamIdById(user.getId());
        typeSelectie.getItems().addAll("epic", "user_story", "taak");
        typeSelectie.setOnAction(event -> {setComboBox(); setLabel();});
        System.out.println(team_id);
    }

    private void setLabel(){
        String keuze = typeSelectie.getValue().toString();
        switch (keuze){
            case "epic"-> koppel.setText("Koppelen aan workitem niet mogelijk");
            case "user_story" -> koppel.setText("Kies een epic om aan te koppelen:");
            case "taak"-> koppel.setText("Kies een userstory om aan te koppelen:");
        }
    }

    private void setComboBox(){
        String keuze = typeSelectie.getValue().toString();
        switch (keuze) {
            case "user_story":
                koppelSelectie.getItems().clear();
                koppelSelectie.getItems().addAll(scrumBoard.getAllEpics(team_id));
                break;
            case "taak":
                koppelSelectie.getItems().clear();
                koppelSelectie.getItems().addAll(scrumBoard.getAllUserstories(team_id));
                break;
            default:
                koppelSelectie.getItems().clear();
                koppelSelectie.getItems().addAll("Geen opties mogelijk");
        }
    }

    private void addWorkitem() {
        String WInaam = workitemNameField.getText();
        String WBeschrijving = workitemBeschrijving.getText();
        String WType = typeSelectie.getSelectionModel().getSelectedItem().toString();
        String WKoppel = koppelSelectie.getSelectionModel().getSelectedItem().toString();

        if (WInaam.isEmpty() || WBeschrijving.isEmpty() || WType.isEmpty()) {
            System.out.println("Lege velden.");
            toegevoegd.setText("Workitem " + WInaam+" is niet toegevoegd, er zijn lege velden");
        } if (WType.equals("epic")) {
            boolean workitemCreated = scrumBoard.createNewEpic(WInaam, WBeschrijving, team_id);
            System.out.println(workitemCreated);
            toegevoegd.setText("Epic " + WInaam+" is toegevoegd");
            workitemNameField.clear();
            workitemBeschrijving.clear();
        } if (WType.equals("user_story")){
            boolean workitemCreated = scrumBoard.createNewUserStory(WInaam, WBeschrijving, scrumBoard.getWorkItemIdByName(WKoppel, "epic"), team_id);
            System.out.println(workitemCreated);
            toegevoegd.setText("Userstory " + WInaam+" is toegevoegd");
            workitemNameField.clear();
            workitemBeschrijving.clear();
        } if (WType.equals("taak")) {
            boolean workitemCreated = scrumBoard.createNewTaak(WInaam, WBeschrijving, scrumBoard.getWorkItemIdByName(WKoppel, "user_story"), team_id);
            System.out.println(workitemCreated);
            toegevoegd.setText("Taak " + WInaam+" is toegevoegd");
            workitemNameField.clear();
            workitemBeschrijving.clear();
        }
    }
}