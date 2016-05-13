package com.example.emil.taskmanager.activities;

import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.fragments.IListFragment;

/**
 * Created by Administrator on 5/11/2016.
 */
public interface ITaskView {

    void EditTask(Task task);

    void ViewDetails(Task task);

    void DeleteTask(IListFragment sender,Task task);

}
