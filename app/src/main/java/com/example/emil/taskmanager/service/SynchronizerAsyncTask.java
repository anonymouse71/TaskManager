package com.example.emil.taskmanager.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.emil.taskmanager.activities.AlarmReceiver;
import com.example.emil.taskmanager.entities.TaskPriority;
import com.example.emil.taskmanager.entities.TriggerType;
import com.example.emil.taskmanager.api.RestTask;
import com.example.emil.taskmanager.dto.AlarmTriggerDTO;
import com.example.emil.taskmanager.dto.TaskDTO;
import com.example.emil.taskmanager.entities.AlarmTrigger;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.utils.UserSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class SynchronizerAsyncTask extends AsyncTask<Void,Void,Void> {

    final String ISO8601DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSZ";

    private OnSyncComplete listener;
    private Context context;
    private AlarmManager alarmManager;

    public SynchronizerAsyncTask(OnSyncComplete listener, Context context) {
        this.listener = listener;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RestTask rest = new RestTask();
        List<TaskDTO> taskList = rest.getTasksById(UserSettings.userId);

        Task.deleteAll(Task.class);
        AlarmTrigger.deleteAll(AlarmTrigger.class);

        for (TaskDTO taskDTO : taskList){

            Task task = new Task(taskDTO.getTitle(),taskDTO.getDescription(), TaskPriority.values()[taskDTO.getPriority()]);
            Task.save(task);
            for (AlarmTriggerDTO alarmTriggerDTO : taskDTO.getTriggers()){

                Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()) ;
                SimpleDateFormat dateformat = new SimpleDateFormat(ISO8601DATEFORMAT, Locale.getDefault());
                try {
                    Date date = dateformat.parse(alarmTriggerDTO.getDate());
                    date.setHours(date.getHours()-1);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                AlarmTrigger trigger = new AlarmTrigger(
                        calendar,
                        alarmTriggerDTO.isRepeat(),
                        alarmTriggerDTO.getInterval(),
                        TriggerType.values()[alarmTriggerDTO.getCategory()]
                );

                trigger.setTask(task);
                AlarmTrigger.save(trigger);

                Intent intent = new Intent(context, AlarmReceiver.class);

                String message = task.getTitle() == null ? "test" : task.getTitle();

                intent.putExtra("Title", "Alarm");
                intent.putExtra("Message", message);
                intent.putExtra("Id", task.getId());

                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, trigger.getId().intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (trigger.isRepeat()) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), trigger.getInterval(), alarmIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
                }
            }
        }



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.syncComplete();
    }

    public interface OnSyncComplete{
        void syncComplete();
    }
}
