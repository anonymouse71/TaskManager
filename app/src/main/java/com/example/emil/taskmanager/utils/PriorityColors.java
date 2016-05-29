package com.example.emil.taskmanager.utils;

import android.graphics.Color;

import com.example.emil.taskmanager.entities.TaskPriority;

/**
 * Created by Administrator on 5/27/2016.
 */
public class PriorityColors {
    public static int getColor(TaskPriority priority){
        switch (priority) {
            case Low:
                return Color.GREEN;
            case Medium:
                return Color.YELLOW;
            case High:
                return Color.RED;
        }
        return Color.WHITE;
    }
}
