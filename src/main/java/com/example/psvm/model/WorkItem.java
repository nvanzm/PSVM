package com.example.psvm.model;

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
