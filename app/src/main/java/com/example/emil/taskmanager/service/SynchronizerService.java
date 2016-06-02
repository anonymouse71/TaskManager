package com.example.emil.taskmanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.emil.taskmanager.api.RestTask;
import com.example.emil.taskmanager.dto.TaskDTO;
import com.example.emil.taskmanager.entities.Task;

import java.util.List;


public class SynchronizerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RestTask rest = new RestTask();
        List<TaskDTO> taskList = rest.getTasks();
        List<TaskDTO> taskList1 = rest.getTasks();
        return 1;
    }
}
