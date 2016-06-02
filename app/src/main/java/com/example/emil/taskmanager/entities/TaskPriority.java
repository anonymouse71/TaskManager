package com.example.emil.taskmanager.entities;

/**
 * Created by Administrator on 5/27/2016.
 */
public enum TaskPriority implements Comparable<TaskPriority> {
    Low(0),Medium(1),High(2);

    private final int value;
    TaskPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
