package com.example.emil.taskmanager.entities;

/**
 * Created by Administrator on 5/18/2016.
 */
public enum TriggerType {
    Alarm(0),Date(1);

    private final int value;
    TriggerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
