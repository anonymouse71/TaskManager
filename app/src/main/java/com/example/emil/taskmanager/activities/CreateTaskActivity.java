package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.fragments.TaskFragment;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Task task = (Task) getIntent().getSerializableExtra("Task");
        if (task != null){
            EditText title = (EditText)findViewById(R.id.CreateTask_Title);
            title.setText(task.getTitle());

            EditText description = (EditText)findViewById(R.id.CreateTask_Description);
            description.setText(task.getDescription());
        }
    }

    public void saveClicked(View v){

        EditText title = (EditText)findViewById(R.id.CreateTask_Title);

        EditText description = (EditText)findViewById(R.id.CreateTask_Description);

        Task task = new Task(title.getText().toString(),description.getText().toString());

        Intent intent = new Intent(this,TaskViewActivity.class);
        intent.putExtra("Task",task);
        startActivity(intent);
    }
}
