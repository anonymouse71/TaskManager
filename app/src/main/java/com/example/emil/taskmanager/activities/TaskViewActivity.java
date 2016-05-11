package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.fragments.IListFragment;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.fragments.SeperatorFragment;
import com.example.emil.taskmanager.fragments.TaskFragment;
import com.example.emil.taskmanager.TaskListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskViewActivity extends AppCompatActivity {

    private TaskListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ListView list = (ListView) findViewById(R.id.listView);


        List<IListFragment> fragments = new ArrayList<>();
        fragments.add(TaskFragment.newInstance("Fitness", "Test"));
        fragments.add(SeperatorFragment.newInstance("a", "b"));

        for (IListFragment frag : fragments){
            onAttachFragment((Fragment)frag);
        }

        final TaskListAdapter adapter = new TaskListAdapter(this,R.layout.fragment_task,fragments);

        list.setAdapter(adapter);
        listAdapter = adapter;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateTaskActivity.class);
                startActivity(intent);

            }
        });

        //If a Task is passed from CreateTask
        Task task = (Task) getIntent().getSerializableExtra("Task");
        if (task != null){
            adapter.add(TaskFragment.newInstance(task.getTitle(),task.getDescription()));
            adapter.notifyDataSetChanged();
        }
    }


    public void EditTaskBtn(View v){
        TextView titleText = (TextView) v.getRootView().findViewById(R.id.Task_Title);

        Task task = new Task();
        task.setTitle(titleText.getText().toString());

        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra("Task",task);
        startActivity(intent);
    }

    public void NewTaskBtn(View v){
        listAdapter.add(TaskFragment.newInstance("abe","fisk"));
        listAdapter.notifyDataSetChanged();
    }

}
