package com.example.psvm.model;

import com.example.psvm.database.WorkItemDB;

import java.util.List;
import java.util.Optional;


public class WorkItem {

    public int id;
    public String naam;
    public String beschrijving;
    public int epic_id;
    public int user_story_id;
    public WorkItemDB workItemDB;
    public WorkItem() {
        workItemDB = new WorkItemDB();
    }

    public List<String> getAllEpics (int team_id) {
        return workItemDB.getAllEpics(team_id);
    }
    public List<String> getAllUserstories (int team_id) {
        return workItemDB.getAllUserstories(team_id);
    }
    public List<String> getAllTaken (int team_id) {
        return workItemDB.getAllTaken(team_id);
    }

    public Integer getWorkItemIdByName(String naam, String type) {
        Optional<Integer> WorkitemIDOPT = workItemDB.getWorkItemIdByName(naam, type);
        if (WorkitemIDOPT.isPresent()) {
            return WorkitemIDOPT.get();
        }
        System.out.println("WorkitemID niet gevonden bij naam: " + naam);
        return null;
    }
}
