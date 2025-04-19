package com.example.psvm.controllers;

import com.example.psvm.database.TeamDB;
import com.example.psvm.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.psvm.model.Team;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import java.util.List;

import static com.example.psvm.Startup.getTeam;
import static com.example.psvm.Startup.getUser;


/**
 * The TeamsController class serves as a JavaFX controller responsible for managing
 * team-related actions in the user interface. This class facilitates the creation of
 * new teams, the retrieving of existing teams for selection, and the process of
 * joining a selected team.

 */
public class TeamsController {
    @FXML
    private Button plusButton;
    @FXML
    private TextField teamNameField;
    @FXML
    private ComboBox teamSelectie;

    private Team team;
    private User user;

    public void initialize() {
        plusButton.setOnAction(event -> addTeam());
        this.team = getTeam();
        this.user = getUser();

        List<String> teamNamen = team.getAllTeamNames();
        teamSelectie.getItems().addAll(teamNamen);

        teamSelectie.setOnAction(event -> joinTeam());
    }

    private void addTeam() {
        String teamNaam = teamNameField.getText().trim();

        if (teamNaam.isEmpty()) {
            System.out.println("Lege teamnaam");
        } else {
            boolean teamCreated = team.createNewTeam(teamNaam);
            if (teamCreated) {
                teamSelectie.getItems().add(team.getName());
            }
        }
    }

    private void joinTeam() {
        String gekozenTeam = teamSelectie.getValue().toString();

        if (gekozenTeam != null) {
            boolean teamJoined = user.joinTeam(gekozenTeam, user.getId());

            if (teamJoined) {
                System.out.println("Team gejoined: " + user.getTeam());
            }
        } else {
            System.out.println("Lege gekozen team");
        }
    }
}
