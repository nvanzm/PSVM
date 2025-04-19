package com.example.psvm.model;

import java.util.Optional;

/**
 * The Epic class represents a type of work item that serves as a container for a set of related tasks or user stories.
 * It extends the WorkItem class, inheriting its properties and methods.
 */
public class Epic extends WorkItem {
    public int id;
    public String naam;
    public String beschrijving;

    public Epic(int id, String naam, String beschrijving) {
        super(naam, beschrijving, id);
    }

    public int getParentId() {
        return 0;
    }
}
