package com.example.emil.taskmanager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.fragments.CreateTriggerFragment;
import com.example.emil.taskmanager.fragments.LoginFragment;
import com.example.emil.taskmanager.fragments.RegisterFragment;
import com.example.emil.taskmanager.fragments.TaskCreateFragment;

/**
 * Created by Administrator on 5/16/2016.
 */
public class CreateTaskPagerAdapter extends FragmentPagerAdapter {

    private  Task task;

    public CreateTaskPagerAdapter(FragmentManager fm, Task task) {
        super(fm);
        this.task = task;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TaskCreateFragment.newInstance(task);
            case 1:
                return CreateTriggerFragment.newInstance("a", "b");
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
