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
import android.widget.Button;
import android.widget.Toast;

import com.example.emil.taskmanager.fragments.DateTriggerFragment;
import com.example.emil.taskmanager.api.RestTask;
import com.example.emil.taskmanager.dto.AlarmTriggerDTO;
import com.example.emil.taskmanager.dto.TaskDTO;
import com.example.emil.taskmanager.fragments.AlarmTriggerFragment;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.TriggerType;
import com.example.emil.taskmanager.adapters.CreateTaskPagerAdapter;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.entities.AlarmTrigger;
import com.example.emil.taskmanager.entities.TaskPriority;
import com.example.emil.taskmanager.fragments.CreateTriggerFragment;
import com.example.emil.taskmanager.fragments.TaskCreateFragment;
import com.example.emil.taskmanager.listeners.ICreateTaskListener;
import com.example.emil.taskmanager.listeners.ITriggerButtonListener;
import com.example.emil.taskmanager.listeners.ITriggerListener;
import com.example.emil.taskmanager.utils.UserSettings;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        viewPager = (ViewPager) findViewById(R.id.CreateTask_ViewPager);

        Serializable id = getIntent().getSerializableExtra("Id");
        if (id != null) {
            currentTask = Task.findById(Task.class, (long) id);
            setTitle("Edit Task");
        } else {
            currentTask = new Task(null, null, TaskPriority.Low);
        }

        List<Fragment> fragments = new ArrayList<>();

        //Add the first fragment
        fragments.add(TaskCreateFragment.newInstance(currentTask));

        if (savedInstanceState == null) {

            List<AlarmTrigger> triggersTemp = currentTask.getTriggerList();

            if (triggersTemp != null) {
                addTriggerFragments(fragments, triggersTemp);
            }

        } else {
            triggers = (List<AlarmTrigger>) savedInstanceState.getSerializable("data");

            addTriggerFragments(fragments, triggers);
        }

        //Add last fragment
        fragments.add(CreateTriggerFragment.newInstance("", ""));

        adapter = new CreateTaskPagerAdapter(this, currentTask, fragments);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addTriggerFragments(List<Fragment> fragments, List<AlarmTrigger> triggersTemp) {
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
        final Task tempTask;

        //If we are creating a new task
        if (currentTask.getTitle() == null) {

            tempTask = task;
            Task.save(tempTask);

        } else {
            tempTask = Task.findById(Task.class, currentTask.getId());
            tempTask.setTitle(task.getTitle());
            tempTask.setDescription(task.getDescription());
            tempTask.setPriority(task.getPriority());

            Task.save(tempTask);

        }

        List<AlarmTriggerDTO> alarmTriggerDTOs = new ArrayList<>();

        //Save triggers
        for (AlarmTrigger trigger : triggers) {
            trigger.setTask(tempTask);
            AlarmTrigger.save(trigger);

            Intent intent = new Intent(this, AlarmReceiver.class);

            String message = task.getTitle() == null ? "test" : task.getTitle();

            intent.putExtra("Title", "Alarm");
            intent.putExtra("Message", message);
            intent.putExtra("Id", tempTask.getId());
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, trigger.getId().intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = trigger.getDate();

            TimeZone tz = TimeZone.getDefault();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'H:mm:ssZ");
            df.setTimeZone(tz);

            String isoDate = df.format(calendar.getTime());

            alarmTriggerDTOs.add(new AlarmTriggerDTO(
                    trigger.isRepeat(),
                    isoDate,
                    (int) trigger.getInterval(),
                    trigger.getCategory().getValue()
            ));

            if (trigger.isRepeat()) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), trigger.getInterval(), alarmIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
            }
        }

        callWebservice(tempTask, alarmTriggerDTOs);
    }

    private void callWebservice(final Task tempTask, List<AlarmTriggerDTO> alarmTriggerDTOs) {
        final Context context = this;

        TaskDTO taskDTO = new TaskDTO(
                UserSettings.userId,
                tempTask.getTitle(),
                tempTask.getDescription(),
                tempTask.getPriority().getValue(),
                alarmTriggerDTOs,
                tempTask.getApiId()
        );

        RestTask rest = new RestTask();

        if (currentTask.getTitle() == null) {

            Call<TaskDTO> call = rest.service.createTask(taskDTO);
            call.enqueue(new Callback<TaskDTO>() {
                @Override
                public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Task Created", Toast.LENGTH_LONG).show();
                        tempTask.setApiId(response.body().get_id());
                        Task.save(tempTask);

                        Intent intent = new Intent(context, TaskViewActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<TaskDTO> call, Throwable t) {

                }
            });
        } else {
            Call<TaskDTO> call = rest.service.editTask(tempTask.getApiId(), taskDTO);
            call.enqueue(new Callback<TaskDTO>() {
                @Override
                public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Task Edited", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, TaskViewActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<TaskDTO> call, Throwable t) {

                }
            });
        }
    }

    private Fragment createFragment(TriggerType triggerType) {
        switch (triggerType) {
            case Alarm:
                return AlarmTriggerFragment.newInstance(new AlarmTrigger(null, true, AlarmManager.INTERVAL_DAY, triggerType));
            case Date:
                return DateTriggerFragment.newInstance(new AlarmTrigger(null, false, 0, triggerType));
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

        if (triggers.contains(trigger)) {
            triggers.set(triggers.indexOf(trigger), trigger);
        } else {
            triggers.add(trigger);
        }

    }
}
