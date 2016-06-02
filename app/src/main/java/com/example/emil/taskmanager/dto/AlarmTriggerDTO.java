package com.example.emil.taskmanager.dto;

public class AlarmTriggerDTO {
    private boolean repeat;
    private String date;
    private int interval;
    private int category;

    public AlarmTriggerDTO(boolean repeat, String date, int interval, int category) {
        this.repeat = repeat;
        this.date = date;
        this.interval = interval;
        this.category = category;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
