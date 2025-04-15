package com.example.psvm.controllers;

import com.example.psvm.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.example.psvm.Startup.getTeam;
import static com.example.psvm.Startup.getUser;

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


    private final ResolutionController resolutionManager = ResolutionController.getInstance();
    private Taak taak;
    private UserStory userStory;
    private Epic epic;
    private Team team;
    private User user;
    private WorkItem workItem;

    @FXML
    public void initialize() {
        WorkItemPlusButton.setOnAction(event -> {addWorkitem();});
        this.team = getTeam();
        this.user = getUser();
        this.epic = new Epic();
        this.userStory = new UserStory();
        this.taak = new Taak();
        this.workItem = new WorkItem();
        typeSelectie.getItems().addAll("epic", "user_story", "taak");
        typeSelectie.setOnAction(event -> {setComboBox();});

    }
    private void setComboBox(){
        String keuze = typeSelectie.getValue().toString();
        switch (keuze) {
            case "user_story":
                koppelSelectie.getItems().clear();
                koppelSelectie.getItems().addAll(workItem.getAllEpics());
                break;
            case "taak":
                koppelSelectie.getItems().clear();
                koppelSelectie.getItems().addAll(workItem.getAllUserstories());
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
        } if (WType.equals("epic")) {
            boolean workitemCreated = epic.createNewWorkItem(WInaam, WBeschrijving, user.getTeamIdById(user.getId()));
            System.out.println(workitemCreated);
        } if (WType.equals("user_story")){
            boolean workitemCreated = userStory.createNewWorkItem(WInaam, WBeschrijving, workItem.getWorkItemIdByName(WKoppel, "epic"), user.getTeamIdById(user.getId()));
            System.out.println(workitemCreated);
        } if (WType.equals("taak")) {
            boolean workitemCreated = taak.createNewWorkItem(WInaam, WBeschrijving, workItem.getWorkItemIdByName(WKoppel, "user_story"), user.getTeamIdById(user.getId()));
            System.out.println(workitemCreated);
        }
    }
}