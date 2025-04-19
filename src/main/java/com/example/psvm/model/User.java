package com.example.psvm.model;

import com.example.psvm.database.LoginDB;
import com.example.psvm.database.UserDB;

import java.util.Optional;

/**
 * Represents a User in the system, handling operations such as logging in, joining teams,
 * and retrieving user or team-related information.
 */
public class User {
    private int id;
    private int team_id;
    private String userName;
    private final LoginDB loginDB = new LoginDB();
    private String team;
    UserDB userDB = new UserDB();

    private void setTeam(String team) {
        this.team = team;
    }
    public String getTeam() {
        return team;
    }

    public boolean joinTeam(String gekozenTeam, int userID) {
        setTeam(gekozenTeam);
        Optional<Integer> teamIDFromTeamName = userDB.getIDFromTeamName(gekozenTeam);
        boolean toegevoegd = userDB.addUserToTeam(userID, teamIDFromTeamName.get());
        if (toegevoegd) {
            System.out.println("User toegevoegd aan team" + gekozenTeam);
            return true;
        }
        System.out.println("User toevoegen gefaald.");
        return false;
    }

    public boolean login(String username) {
        if (username.isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }

        Optional<Integer> userIdOpt = loginDB.getUserIdByUsername(username);

        if (userIdOpt.isPresent()) {
            this.id = userIdOpt.get();
            System.out.println("User ID: " + this.id);
            return true;
        } else {
            throw new RuntimeException("Invalid username");
        }
    }

    public String getNameById(int id) {
        Optional<String> userIdOpt = loginDB.getUsernameById(id);

        if (userIdOpt.isPresent()) {
            return userIdOpt.get();
        } else {
            throw new RuntimeException("Invalid username");
        }
    }

    public int getTeamIdById(int team_id) {
        Optional<String> teamIdOpt = loginDB.getTeamIdById(team_id);
        if (teamIdOpt.isPresent()) {
            return Integer.parseInt(teamIdOpt.get());
        }
        System.out.println(team_id);
        return team_id;
    }

    public int getId() {
        return id;
    }

    public int getTeamId() {
        return team_id;
    }

    public void setId(int i) {
        this.id = i;
    }
}
