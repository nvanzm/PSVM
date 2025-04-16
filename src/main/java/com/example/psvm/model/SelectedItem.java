package com.example.psvm.model;

public class SelectedItem {
    private final int id;
    private final String type;

    public SelectedItem(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
