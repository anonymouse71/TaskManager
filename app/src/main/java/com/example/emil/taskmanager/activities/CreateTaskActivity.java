package com.example.emil.taskmanager.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.emil.taskmanager.DateTriggerFragment;
import com.example.emil.taskmanager.fragments.AlarmTriggerFragment;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.TriggerType;
import com.example.emil.taskmanager.adapters.CreateTaskPagerAdapter;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.entities.AlarmTrigger;
import com.example.emil.taskmanager.entities.TaskPriority;
import com.example.emil.taskmanager.fragments.CreateTriggerFragment;
import com.example.emil.taskmanager.fragments.TaskCreateFragment;
import com.example.emil.taskmanager.listeners.ICreateTaskListener;
import com.example.emil.taskmanager.listeners.ITriggerButtonListener;
import com.example.emil.taskmanager.listeners.ITriggerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateTaskActivity extends AppCompatActivity implements ICreateTaskListener, ITriggerButtonListener, ITriggerListener, Serializable {

    private Task currentTask;

    private ViewPager viewPager;
    private CreateTaskPagerAdapter adapter;

    private AlarmManager alarmManager;
    List<AlarmTrigger> triggers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Serializable id = getIntent().getSerializableExtra("Id");
        if (id != null) {
            currentTask = Task.findById(Task.class, (long) id);
            setTitle("Edit TaskDTO");
        } else {
            currentTask = new Task(null,null, TaskPriority.Low);
        }

        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.CreateTask_ViewPager);

        List<Fragment> fragments = new ArrayList<>();


        fragments.add(TaskCreateFragment.newInstance(currentTask));

        if (savedInstanceState == null) {

            List<AlarmTrigger> triggersTemp = currentTask.getTriggerList();

            if (triggersTemp != null) {
                for (AlarmTrigger trigger : triggersTemp) {
                    triggers.add(trigger);
                    switch (trigger.getCategory()) {
                        case Alarm:
                            fragments.add(AlarmTriggerFragment.newInstance(trigger));
                            break;
                        case Date:
                            fragments.add(DateTriggerFragment.newInstance(trigger));
                            break;
                    }
                }

            }

            fragments.add(CreateTriggerFragment.newInstance("", ""));

        } else {
            triggers = (List<AlarmTrigger>) savedInstanceState.getSerializable("data");

            for (AlarmTrigger trigger : triggers){
                switch (trigger.getCategory()) {
                    case Alarm:
                        fragments.add(AlarmTriggerFragment.newInstance(trigger));
                        break;
                    case Date:
                        fragments.add(DateTriggerFragment.newInstance(trigger));
                        break;
                }
            }

            fragments.add(CreateTriggerFragment.newInstance("", ""));
        }


        adapter = new CreateTaskPagerAdapter(this, currentTask, fragments);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("data", (Serializable) triggers);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_trigger_add) {
            viewPager.setCurrentItem(viewPager.getChildCount());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void savePressed(Task task) {
        Task tempTask;

        //If we are creating a new task
        if (currentTask.getTitle() == null) {

            tempTask = task;
            Task.save(tempTask);

            Intent intent = new Intent(this, TaskViewActivity.class);
            startActivity(intent);
        } else {
            tempTask = Task.findById(Task.class, currentTask.getId());
            tempTask.setTitle(task.getTitle());
            tempTask.setDescription(task.getDescription());
            tempTask.setPriority(task.getPriority());

            Task.save(tempTask);

            Intent intent = new Intent(this, TaskViewActivity.class);
            startActivity(intent);
        }

        //Save triggers
        for (AlarmTrigger trigger : triggers){
            trigger.setTask(tempTask);
            AlarmTrigger.save(trigger);

            Intent intent = new Intent(this, AlarmReceiver.class);

            String message = task.getTitle() == null ? "test" : task.getTitle();

            intent.putExtra("Title","Alarm");
            intent.putExtra("Message", message);
            intent.putExtra("Id",tempTask.getId());
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, trigger.getId().intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = trigger.getDate();


            if (trigger.isRepeat()) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), trigger.getInterval(), alarmIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
            }
        }

    }

    private Fragment createFragment(TriggerType triggerType) {
        switch (triggerType) {
            case Alarm:
                return AlarmTriggerFragment.newInstance(new AlarmTrigger(null,true,AlarmManager.INTERVAL_DAY,triggerType));
            case Date:
                return DateTriggerFragment.newInstance(new AlarmTrigger(null,false,0,triggerType));
        }
        return null;
    }

    @Override
    public void triggerButtonPressed(TriggerType triggerType) {
        adapter.remove(viewPager.getCurrentItem());

        adapter.add(createFragment(triggerType));

        adapter.add(CreateTriggerFragment.newInstance("", ""));

        adapter.notifyDataSetChanged();
    }


    @Override
    public void saveTrigger(AlarmTrigger trigger) {

        viewPager.setCurrentItem(0);

        if (triggers.contains(trigger)){
            triggers.set(triggers.indexOf(trigger), trigger);
        }else {
            triggers.add(trigger);
        }

    }
}
