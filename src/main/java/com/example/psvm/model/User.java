package com.example.psvm.model;

import com.example.psvm.controllers.LoginController;
import com.example.psvm.database.LoginDB;
import javafx.event.ActionEvent;

public class User {
    private int id;
    private String userName;
    private final LoginDB loginDB = new LoginDB();

//    public User() {
//
//    }

    public boolean login(String username) {
        if (username.isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }

        // Als de gebruiker geldig is in de database
        if (loginDB.isUserValid(username)) {
            this.userName = username;
            return true;
        } else {
            throw new RuntimeException("Invalid username");
        }
    }


    public boolean isUserLoggedIn(String username) {
        return this.userName != null && this.userName.equals(username);
    }
}
