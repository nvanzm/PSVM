package com.example.psvm.model;

import java.util.Optional;

/**
 * The Taak class represents a task within a project management context.
 * It extends the WorkItem class, which provides the base properties and methods
 * for general work items. This class specifically adds functionality for tasks
 * associated with a user story.
 */
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
