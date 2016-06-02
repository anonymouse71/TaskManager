package com.example.emil.taskmanager.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nikolaj on 5/11/2016.
 */
public class Task extends SugarRecord implements Serializable {
    private String title;
    private String description;
    private TaskPriority priority;

    @Ignore
    private boolean menuOpen;

    public Task(String title, String description, TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public Task() {

    }

    public List<AlarmTrigger> getTriggers() {
        if (getId() != null){
            return AlarmTrigger.find(AlarmTrigger.class, "task = ?", getId().toString());
        }
        return  null;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
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

    public boolean isMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(boolean menuOpen) {
        this.menuOpen = menuOpen;
    }


}
