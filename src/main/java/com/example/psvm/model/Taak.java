package com.example.psvm.model;

import java.util.Optional;

public class Taak extends WorkItem {

    public boolean createNewWorkItem(String naam, String beschrijving, int user_story_id, int team_id) {
        Optional<Integer> UserStories = workItemDB.addNewTaak(naam, beschrijving, user_story_id, team_id);
        System.out.println("Workitem added");

        if (UserStories.isPresent()) {
            this.id = UserStories.get();
            this.naam = naam;
            this.beschrijving = beschrijving;
            this.user_story_id = user_story_id;
            System.out.println("Workitem added: " + naam);
            return true;
        } else {
            System.out.println("Workitem not added");
            return false;
        }
    }
}
