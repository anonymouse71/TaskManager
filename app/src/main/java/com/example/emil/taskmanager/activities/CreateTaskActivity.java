package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.Task;

import java.io.Serializable;

public class CreateTaskActivity extends AppCompatActivity {

    private Task task;

    private EditText titleText;
    private EditText descripitionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        titleText = (EditText)findViewById(R.id.CreateTask_Title);
        descripitionText = (EditText)findViewById(R.id.CreateTask_Description);

        Serializable id = getIntent().getSerializableExtra("Id");
        if (id != null){

            task = Task.findById(Task.class,(long)id);

            titleText.setText(task.getTitle());
            descripitionText.setText(task.getDescription());
        }
    }

    public void saveClicked(View v){

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
    }
}
