package com.example.psvm.model;

import java.util.Optional;

public class Epic extends WorkItem {

    public boolean createNewWorkItem(String naam, String beschrijving, int team_id) {
        Optional<Integer> Epics = workItemDB.addNewEpic(naam, beschrijving, team_id);
        System.out.println("Workitem added");

        if (Epics.isPresent()) {
            this.id = Epics.get();
            this.naam = naam;
            this.beschrijving = beschrijving;
            System.out.println("Workitem added: " + naam);
            return true;
        } else {
            System.out.println("Workitem not added");
            return false;
        }
    }
}
