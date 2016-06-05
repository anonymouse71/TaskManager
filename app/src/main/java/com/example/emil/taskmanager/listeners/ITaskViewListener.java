package com.example.emil.taskmanager.listeners;

import com.example.emil.taskmanager.entities.Task;

/**
 * Created by Administrator on 5/11/2016.
 */
public interface ITaskViewListener {

    void EditTask(Task task);

    void ViewDetails(Task task);

    void DeleteTask(Task task);

}
