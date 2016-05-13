package com.example.emil.taskmanager.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.Task;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleText = (TextView)findViewById(R.id.Task_Details_Title);
        descripitionText = (TextView)findViewById(R.id.Task_Details_Description);

        Serializable id = getIntent().getSerializableExtra("Id");
        if (id != null){

            task = Task.findById(Task.class, (long) id);

            titleText.setText(task.getTitle());
            descripitionText.setText(task.getDescription());
        }
    }

}
