package com.example.psvm.model;

public class Message {
    private int id;
    private String text;
    private int userId;
    private int team_id;
    private int parentId;
    private Integer itemId;
    private String itemType;
    private WorkItem workItem;


    // Getters en setters
    public Object getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getTeamId() {
        return team_id;
    }

    public void setTeamId(int team_id) {
        this.team_id = team_id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public WorkItem getWorkItem() {
        return workItem;
    }

    public void setWorkItem(WorkItem workItem) {
        this.workItem = workItem;
    }

    public String getItemType() {
        return itemType;
    }

    public String getTypeLabel() {
        if (itemType == null) {
            return "Algemeen";
        }

        switch (itemType.toLowerCase()) {
            case "epic": return "Epic";
            case "user_story": return "User Story";
            case "taak": return "Taak";
            default: return "Algemeen";
        }
    }

    public String getStyleClassForType() {
        if (itemType == null) {
            return "algemeen-circle";
        }

        switch (itemType.toLowerCase()) {
            case "epic": return "epic-circle";
            case "user_story": return "us-circle";
            case "taak": return "taak-circle";
            default: return "algemeen-circle";
        }
    }

}
