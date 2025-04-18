package com.example.psvm.model;

import java.util.Optional;

public class UserStory extends WorkItem {
    public int epicId;

    public UserStory(int id, String naam, String beschrijving, int epicId) {
        super(naam, beschrijving, id);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return naam;
    }

    public int getParentId() {
        return epicId;
    }
}
