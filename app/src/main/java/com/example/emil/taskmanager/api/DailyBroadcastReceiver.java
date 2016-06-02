package com.example.emil.taskmanager.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.emil.taskmanager.service.SynchronizerAsyncTask;

public class DailyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, SynchronizerAsyncTask.class);
        context.startService(serviceIntent);
    }

}
