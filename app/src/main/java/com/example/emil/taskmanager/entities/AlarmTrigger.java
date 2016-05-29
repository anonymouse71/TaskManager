package com.example.emil.taskmanager.entities;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import com.example.emil.taskmanager.ITrigger;
import com.example.emil.taskmanager.TriggerType;
import com.example.emil.taskmanager.activities.AlarmReceiver;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Administrator on 5/18/2016.
 */
public class AlarmTrigger extends SugarRecord implements Serializable{

    private Calendar date;

    private boolean repeating;

    private long interval;

    private TriggerType category;

    private Task task;

    /**
     *
     * @param date
     * @param repeating
     * @param interval
     * @param category
     */
    public AlarmTrigger(Calendar date, boolean repeating, long interval, TriggerType category) {
        this.date = date;
        this.repeating = repeating;
        this.interval = interval;
        this.category = category;
    }

    public AlarmTrigger() {
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public TriggerType getCategory() {
        return category;
    }

    public void setCategory(TriggerType category) {
        this.category = category;
    }
}
