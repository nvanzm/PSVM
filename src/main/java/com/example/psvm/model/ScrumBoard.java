package com.example.psvm.model;

import com.example.psvm.database.WorkItemDB;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class ScrumBoard {
    private final WorkItemDB workItemDB;

    public HashMap<Integer, Epic> epicMap = new HashMap<>();
    public HashMap<Integer, UserStory> userStoryMap = new HashMap<>();
    public HashMap<Integer, Taak> taakMap = new HashMap<>();


    public ScrumBoard() {
        workItemDB = new WorkItemDB();
    }

    public void load(int teamId) {
        getAllEpics(teamId);
        getAllTaken(teamId);
        getAllUserstories(teamId);
    }

    public List<Epic> getAllEpics(int team_id) {
        List<Epic> epics = workItemDB.getAllEpics(team_id);
        epicMap.clear(); // Clear old data before updating
        for (Epic epic : epics) {
            epicMap.put(epic.getId(), epic);
        }
        return epics;
    }

    public List<UserStory> getAllUserstories(int team_id) {
        List<UserStory> userStories = workItemDB.getAllUserstories(team_id);
        userStoryMap.clear();
        for (UserStory userStory : userStories) {
            userStoryMap.put(userStory.getId(), userStory);
        }
        return userStories;
    }

    public List<Taak> getAllTaken(int team_id) {
        List<Taak> taken = workItemDB.getAllTaken(team_id);
        taakMap.clear();
        for (Taak taak : taken) {
            taakMap.put(taak.getId(), taak);
        }
        return taken;
    }

    public Integer getWorkItemIdByName(String naam, String type) {
        Optional<Integer> WorkitemIDOPT = workItemDB.getWorkItemIdByName(naam, type);
        if (WorkitemIDOPT.isPresent()) {
            return WorkitemIDOPT.get();
        }
        System.out.println("WorkitemID niet gevonden bij naam: " + naam);
        return null;
    }

    public String getEpicNameById(int id) {
        Epic epic = epicMap.get(id);
        return epic != null ? epic.getNaam() : "Onbekende Epic";
    }

    public UserStory getUserStoryById(int id) {
        UserStory userStory = userStoryMap.get(id);
        return userStory != null ? userStory : null;
    }

    public String getUserStoryNameById(int id) {
        UserStory userStory = userStoryMap.get(id);
        return userStory != null ? userStory.getNaam() : "Onbekende User Story";
    }


    public String getTaakNameById(int id) {
        Taak taak = taakMap.get(id);
        return taak != null ? taak.getNaam() : "Onbekende Taak";
    }


    public boolean createNewEpic(String naam, String beschrijving, int team_id) {
        Optional<Integer> Epics = workItemDB.addNewEpic(naam, beschrijving, team_id);
        System.out.println("Workitem added");

        if (Epics.isPresent()) {
            System.out.println("Workitem added: " + naam);
            return true;
        } else {
            System.out.println("Workitem not added");
            return false;
        }
    }

    public boolean createNewTaak(String naam, String beschrijving, int user_story_id, int team_id) {
        Optional<Integer> UserStories = workItemDB.addNewTaak(naam, beschrijving, user_story_id, team_id);
        System.out.println("Workitem added");

        if (UserStories.isPresent()) {
            System.out.println("Workitem added: " + naam);
            return true;
        } else {
            System.out.println("Workitem not added");
            return false;
        }
    }

    public boolean createNewUserStory(String naam, String beschrijving, int epic_id, int team_id) {
        Optional<Integer> UserStories = workItemDB.addNewUserStory(naam, beschrijving, epic_id, team_id);
        System.out.println("Workitem added");

        if (UserStories.isPresent()) {
//            this.id = UserStories.get();
//            this.naam = naam;
//            this.beschrijving = beschrijving;
//            this.epic_id = epic_id;
            System.out.println("Workitem added: " + naam);
            return true;
        } else {
            System.out.println("Workitem not added");
            return false;
        }
    }

    public List<Message> enrichMessagesWithWorkItems(List<Message> messages) {
        for (Message message : messages) {
            if (message.getItemId() == null || message.getItemType() == null) continue;

            WorkItem item = null;
            switch (message.getItemType()) {
                case "epic":
                    item = epicMap.get(message.getItemId());
                    break;
                case "user_story":
                    item = userStoryMap.get(message.getItemId());
                    break;
                case "taak":
                    item = taakMap.get(message.getItemId());
                    break;
            }

            if (item == null) {
                System.err.printf("⚠️  Missing WorkItem: type=%s, id=%d%n",
                        message.getItemType(), message.getItemId());
            }

            message.setWorkItem(item);
        }
        return messages;
    }


}
