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
                return Color.parseColor("#4CAF50");
            case Medium:
                return Color.parseColor("#FFEB3B");
            case High:
                return Color.parseColor("#f44336");
        }
        return Color.WHITE;
    }
}
