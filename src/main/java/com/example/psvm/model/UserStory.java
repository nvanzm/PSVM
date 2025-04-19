package com.example.psvm.model;

import java.util.Optional;

/**
 * Represents a user story in a project management context.
 * A user story is considered a smaller, manageable work item
 * that belongs to an epic. This class extends the WorkItem class,
 * which provides common functionality related to work items.
 */
public class UserStory extends WorkItem {
    public int epicId;

    public UserStory(int id, String naam, String beschrijving, int epicId) {
        super(naam, beschrijving, Integer.valueOf(id));
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
