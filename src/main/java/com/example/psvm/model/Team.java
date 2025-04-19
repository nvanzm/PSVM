package com.example.psvm.model;

import com.example.psvm.database.TeamDB;

import java.util.List;
import java.util.Optional;

/**
 * The Team class represents a project team within an application.
 * It provides methods to interact with team data, including creating new teams,
 * retrieving team information, and managing team attributes.
 * The class integrates with a TeamDB instance to perform database operations.
 */
public class Team {
    private int id;
    private String name;
    TeamDB teamDB;

    public Team() {
//        this.id = id;
//        this.name = name;
        this.teamDB = new TeamDB();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean createNewTeam(String teamNaam) {
//        this.name = teamNaam;
//        System.out.println(getName());

        Optional<Integer> teamIdOpt = teamDB.addNewTeamToDB(teamNaam);
        System.out.println("Team added");

        if (teamIdOpt.isPresent()) {
            this.id = teamIdOpt.get();
            this.name = teamNaam;
            System.out.println("Team added: " + teamNaam);
            return true;
        } else {
            System.out.println("Team not added");
            return false;
        }
    }

    public String getTeamNameById(int team_id) {
        Optional<String> teamNameOpt = teamDB.getTeamNameById(team_id);
        if (teamNameOpt.isPresent()) {
            return teamNameOpt.get();
        }
        System.out.println("Teamnaam niet gevonden voor ID: " + team_id);
        return null;
    }

    public List<String> getAllTeamNames () {
        return teamDB.getAllTeamNames();
    }
}
