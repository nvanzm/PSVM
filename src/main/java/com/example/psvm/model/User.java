package com.example.psvm.model;

import com.example.psvm.controllers.LoginController;
import com.example.psvm.database.LoginDB;
import javafx.event.ActionEvent;

import java.util.Optional;

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

        Optional<Integer> userIdOpt = loginDB.getUserIdByUsername(username);

        if (userIdOpt.isPresent()) {
            this.id = userIdOpt.get();
            System.out.println(this.id);
            return true;
        } else {
            throw new RuntimeException("Invalid username");
        }
    }

    public boolean isUserLoggedIn(String username) {
        return this.userName != null && this.userName.equals(username);
    }

    public String getNameById(int id) {
        Optional<String> userIdOpt = loginDB.getUsernameById(id);

        if (userIdOpt.isPresent()) {
            System.out.println(this.id);
            return userIdOpt.get();
        } else {
            throw new RuntimeException("Invalid username");
        }
    }

    public int getId() {
        return id;
    }
}
