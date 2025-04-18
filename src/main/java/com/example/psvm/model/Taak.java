package com.example.psvm.model;

import java.util.Optional;

public class Taak extends WorkItem {
    public int user_story_id;

    public Taak(int id, String naam, String beschrijving, int userStoryId) {
        super(naam, beschrijving, id);
        this.user_story_id = userStoryId;
    }

    public int getParentId() {
        return user_story_id;
    }
}
