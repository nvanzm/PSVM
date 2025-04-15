package com.example.psvm.model;

import java.util.Optional;

public class UserStory extends WorkItem {

    public boolean createNewWorkItem(String naam, String beschrijving, int epic_id, int team_id) {
        Optional<Integer> UserStories = workItemDB.addNewUserStory(naam, beschrijving, epic_id, team_id);
        System.out.println("Workitem added");

        if (UserStories.isPresent()) {
            this.id = UserStories.get();
            this.naam = naam;
            this.beschrijving = beschrijving;
            this.epic_id = epic_id;
            System.out.println("Workitem added: " + naam);
            return true;
        } else {
            System.out.println("Workitem not added");
            return false;
        }
    }
}
