package com.example.emil.taskmanager.dto;

import com.example.emil.taskmanager.entities.AlarmTrigger;

import java.util.List;

public class TaskDTO {

    private String title;
    private String description;
    private List<AlarmTriggerDTO> triggers;
    private String userID;
    private String _id;

    public TaskDTO(String userID, String title, String description, List<AlarmTriggerDTO> triggers, String id) {
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.triggers = triggers;
        _id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AlarmTriggerDTO> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<AlarmTriggerDTO> triggers) {
        this.triggers = triggers;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
