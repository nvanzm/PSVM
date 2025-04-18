package com.example.psvm.model;

public abstract class WorkItem {
    protected String naam;
    protected String beschrijving;
    protected int id;

    public WorkItem(String naam, String beschrijving, int id) {
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

    public int getId() {
        return id;
    }

    public abstract int getParentId();
}
