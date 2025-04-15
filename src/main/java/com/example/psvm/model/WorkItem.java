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

    public List<String> getAllEpics () {
        return workItemDB.getAllEpics();
    }
    public List<String> getAllUserstories () {
        return workItemDB.getAllUserstories();
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
