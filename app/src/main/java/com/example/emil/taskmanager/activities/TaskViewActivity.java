package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.fragments.IListFragment;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.fragments.TaskFragment;
import com.example.emil.taskmanager.TaskListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskViewActivity extends AppCompatActivity implements ITaskView {

    private TaskListAdapter listAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        startListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateTaskActivity.class);
                startActivity(intent);

            }
        });


    }

    private void startListView() {
        List<Task> tasks = Task.listAll(Task.class);
        List<IListFragment> fragments = new ArrayList<>();

        for (Task task : tasks){
            fragments.add(TaskFragment.newInstance(task));
        }

        final TaskListAdapter adapter = new TaskListAdapter(this, R.layout.fragment_task,fragments);

        listView.setAdapter(adapter);
        listAdapter = adapter;
    }

    public void NewTaskBtn(View v){
        Task task = new Task("Test","Test");
        Task.save(task);

        listAdapter.add(TaskFragment.newInstance(new Task("Abe","Fisk")));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void EditTask(Task task) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra("Id", task.getId());
        startActivity(intent);
    }

    @Override
    public void ViewDetails(Task task) {
        Intent intent = new Intent(this, TaskDetailsActivity.class);
        intent.putExtra("Id", task.getId());
        startActivity(intent);
    }

    @Override
    public void DeleteTask(IListFragment sender,Task task) {
        task = Task.findById(Task.class,task.getId());
        Task.delete(task);

        listAdapter.remove(sender);
    }
}
