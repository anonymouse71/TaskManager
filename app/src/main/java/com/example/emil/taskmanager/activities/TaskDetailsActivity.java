package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.utils.PriorityColors;

import org.w3c.dom.Text;

import java.io.Serializable;

public class TaskDetailsActivity extends AppCompatActivity {

    private TextView titleText;
    private TextView descripitionText;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        titleText = (TextView)findViewById(R.id.Task_Details_Title);
        descripitionText = (TextView)findViewById(R.id.Task_Details_Description);
        FrameLayout priorityBar = (FrameLayout) findViewById(R.id.Task_Details_PriorityBar);

        Serializable id = getIntent().getSerializableExtra("Id");
        if (id != null){

            task = Task.findById(Task.class, (long) id);

            titleText.setText(task.getTitle());
            descripitionText.setText(task.getDescription());
            priorityBar.setBackgroundColor(PriorityColors.getColor(task.getPriority()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_task_edit) {
            Intent intent = new Intent(this, CreateTaskActivity.class);
            intent.putExtra("Id", task.getId());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
