package com.example.emil.taskmanager.entities;

import com.example.emil.taskmanager.TriggerType;

public class TriggerButton{
    private int imageId;

    private String text;

    private TriggerType type;

    public TriggerButton(int imageId, String text, TriggerType type) {
        this.imageId = imageId;
        this.text = text;
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TriggerType getType() {
        return type;
    }

    public void setType(TriggerType type) {
        this.type = type;
    }
}
