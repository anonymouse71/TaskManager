package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.entities.TaskPriority;
import com.example.emil.taskmanager.fragments.IListFragment;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.fragments.TaskFragment;
import com.example.emil.taskmanager.adapters.TaskListAdapter;
import com.example.emil.taskmanager.listeners.ITaskViewListener;

import java.util.ArrayList;
import java.util.List;

public class TaskViewActivity extends AppCompatActivity implements ITaskViewListener {

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
    }

    private void startListView() {
        List<Task> tasks = Task.listAll(Task.class);
        List<IListFragment> fragments = new ArrayList<>();

        final TaskListAdapter adapter = new TaskListAdapter(this, R.layout.fragment_task, tasks);

        listView.setAdapter(adapter);
        listAdapter = adapter;
    }

    public void NewTaskBtn(View v) {
        Task task = new Task("Test", "Test", TaskPriority.Low);
        Task.save(task);

        listAdapter.add(task);
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
    public void DeleteTask( Task task) {
        task = Task.findById(Task.class, task.getId());
        Task.delete(task);

        listAdapter.remove(task);
    }

    /**
     * Inflates the menu and adds items to the action bar.
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_view, menu);
        return true;
    }

    /**
     * Handles action bar item clicks.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile_view) {
            Intent intent = new Intent(this, ProfileViewActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_task_add) {
            Intent intent = new Intent(this, CreateTaskActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
