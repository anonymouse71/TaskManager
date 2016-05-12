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

    private Task task;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        task = (Task) getIntent().getSerializableExtra("Task");
        if (task != null){
            EditText title = (EditText)findViewById(R.id.CreateTask_Title);
            title.setText(task.getTitle());

            position = (int) getIntent().getSerializableExtra("Position");

            EditText description = (EditText)findViewById(R.id.CreateTask_Description);
            description.setText(task.getDescription());
        }
    }

    public void saveClicked(View v){
        if (task == null){
            EditText title = (EditText)findViewById(R.id.CreateTask_Title);

            EditText description = (EditText)findViewById(R.id.CreateTask_Description);

            Task task = new Task(title.getText().toString(),description.getText().toString());

            Intent intent = new Intent(this,TaskViewActivity.class);
            intent.putExtra("Task",task);
            startActivity(intent);
        } else {
            EditText title = (EditText)findViewById(R.id.CreateTask_Title);

            EditText description = (EditText)findViewById(R.id.CreateTask_Description);

            task.setTitle(title.getText().toString());
            task.setDescription(description.getText().toString());

            Intent intent = new Intent(this,TaskViewActivity.class);
            intent.putExtra("Task",task);
            intent.putExtra("Position",position);
            startActivity(intent);
        }
    }
}
