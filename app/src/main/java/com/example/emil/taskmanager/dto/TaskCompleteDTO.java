package com.example.emil.taskmanager.dto;

/**
 * Created by Administrator on 6/5/2016.
 */
public class TaskCompleteDTO {

    private boolean completed;

    public TaskCompleteDTO(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
