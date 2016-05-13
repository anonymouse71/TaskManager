package com.example.emil.taskmanager.entities;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Nikolaj on 5/11/2016.
 */
public class Task extends SugarRecord implements Serializable {
    private String title;
    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task() {

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
}
