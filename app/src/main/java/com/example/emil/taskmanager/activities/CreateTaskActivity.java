package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.adapters.CreateTaskPagerAdapter;
import com.example.emil.taskmanager.adapters.TriggerViewAdapter;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.fragments.CreateTriggerFragment;
import com.example.emil.taskmanager.fragments.TaskCreateFragment;
import com.example.emil.taskmanager.listeners.ICreateTaskListener;
import com.example.emil.taskmanager.listeners.ITriggerButtonListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateTaskActivity extends AppCompatActivity implements ICreateTaskListener, ITriggerButtonListener {

    private Task task;

    private EditText titleText;
    private EditText descripitionText;
    private ViewPager viewPager;
    private CreateTaskPagerAdapter adapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Serializable id = getIntent().getSerializableExtra("Id");
        if (id != null){
            task = Task.findById(Task.class,(long)id);
        }else {
            task = new Task();
        }

        viewPager = (ViewPager) findViewById(R.id.CreateTask_ViewPager);

        fragments = new ArrayList<>();
        fragments.add(TaskCreateFragment.newInstance(task));
        fragments.add(CreateTriggerFragment.newInstance("", ""));

        adapter = new CreateTaskPagerAdapter(this,task, fragments);
        viewPager.setAdapter(adapter);

    }

/*    public void saveClicked(View v){

        if (task == null){

            Task task = new Task(titleText.getText().toString(),descripitionText.getText().toString());
            Task.save(task);

            Intent intent = new Intent(this,TaskViewActivity.class);
            startActivity(intent);
        } else {
            Task tempTask = Task.findById(Task.class,task.getId());
            tempTask.setTitle(titleText.getText().toString());
            tempTask.setDescription(descripitionText.getText().toString());

            Task.save(tempTask);

            Intent intent = new Intent(this,TaskViewActivity.class);
            startActivity(intent);
        }
    }*/

    @Override
    public void savePressed(String title, String description) {
        if (task.getTitle() == null){

            Task task = new Task(title,description);
            Task.save(task);

            Intent intent = new Intent(this,TaskViewActivity.class);
            startActivity(intent);
        } else {
            Task tempTask = Task.findById(Task.class,task.getId());
            tempTask.setTitle(title);
            tempTask.setDescription(description);

            Task.save(tempTask);

            Intent intent = new Intent(this,TaskViewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void Click() {
        fragments.remove(1);
        fragments.add(TaskCreateFragment.newInstance(task));
        adapter.notifyDataSetChanged();
        /*viewPager.removeViewAt(viewPager.getCurrentItem());
        viewPager.addView();*/
    }
}
