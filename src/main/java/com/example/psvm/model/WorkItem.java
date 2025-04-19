package com.example.psvm.model;

/**
 * The WorkItem class represents an abstract base class for work items
 * in a project management context. This class defines the common properties
 * and behavior of work items, such as name, description, and ID. Specific
 * types of work items should extend this class and implement its abstract
 * methods.
 */
public abstract class WorkItem {
    protected String naam;
    protected String beschrijving;
    protected Integer id;

    public WorkItem(String naam, String beschrijving, Integer id) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.id = id;
    }

    @Override
    public String toString() {
        return naam;
    }

    public String getNaam() {
        return naam;
    }

    public Integer getId() {
        return id;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public abstract int getParentId();
}
