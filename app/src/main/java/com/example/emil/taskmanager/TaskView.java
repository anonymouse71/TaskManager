package com.example.emil.taskmanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TaskView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ListView list = (ListView) findViewById(R.id.listView);


        List<IListFragment> fragments = new ArrayList<>();
        fragments.add(new TaskFragment());
        fragments.add(new SeperatorFragment());

        final TaskListAdapter adapter = new TaskListAdapter(this,R.layout.fragment_task,fragments);

        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IListFragment fragment = new TaskFragment();
                adapter.add(fragment);
                adapter.notifyDataSetChanged();

            }
        });
    }

}
