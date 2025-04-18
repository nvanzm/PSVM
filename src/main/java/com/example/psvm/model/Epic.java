package com.example.psvm.model;

import java.util.Optional;

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
