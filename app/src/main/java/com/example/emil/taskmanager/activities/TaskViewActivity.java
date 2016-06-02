package com.example.emil.taskmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emil.taskmanager.api.RestTask;
import com.example.emil.taskmanager.dto.TaskDTO;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.entities.TaskPriority;
import com.example.emil.taskmanager.fragments.IListFragment;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.fragments.TaskFragment;
import com.example.emil.taskmanager.adapters.TaskListAdapter;
import com.example.emil.taskmanager.listeners.ITaskViewListener;
import com.example.emil.taskmanager.service.TaskService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskViewActivity extends AppCompatActivity implements ITaskViewListener {

    private TaskListAdapter listAdapter;
    private ListView listView;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        if (savedInstanceState != null){
            tasks = (List<Task>) savedInstanceState.getSerializable("Data");
        }

        startListView();
    }

    private void startListView() {
        if (tasks == null){
            tasks = Task.listAll(Task.class);
        }


        final TaskListAdapter adapter = new TaskListAdapter(this, R.layout.fragment_task, tasks);

        listView.setAdapter(adapter);
        listAdapter = adapter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Data", (Serializable) tasks);
    }

    private boolean prioritySort;
    private boolean nameSort;

    public void SortAlphabetically(View v) {
        listAdapter.sort(new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                return nameSort ?
                        rhs.getTitle().compareToIgnoreCase(lhs.getTitle()) :
                        lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
            }
        });

        nameSort = !nameSort;
        prioritySort = false;
    }

    public void SortByPriority(View v){
        listAdapter.sort(new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                if (prioritySort){
                    if (lhs.getPriority().getValue() <= rhs.getPriority().getValue()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }else {
                    if (lhs.getPriority().getValue() >= rhs.getPriority().getValue()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });

        prioritySort = !prioritySort;
        nameSort = false;
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
    public void DeleteTask(Task task) {

        final Context context = this;

        RestTask rest = new RestTask();
        Call<TaskDTO> call = rest.service.deleteTask(task.getApiId());
        call.enqueue(new Callback<TaskDTO>() {
            @Override
            public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                Toast.makeText(context,"Task Deleted",Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<TaskDTO> call, Throwable t) {

            }
        });

        Task tempTask = Task.findById(Task.class, task.getId());
        Task.delete(tempTask);

        tasks.remove(task);
        listAdapter.notifyDataSetChanged();
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
