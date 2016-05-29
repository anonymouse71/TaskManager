package com.example.emil.taskmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.emil.taskmanager.activities.TaskViewActivity;

public class NotificationService extends Service {

    private static NotificationService instance = null;
    protected NotificationService() {
        // Exists only to defeat instantiation.
    }
    public static NotificationService getInstance() {
        if(instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;

    }



    public void createNotification(String title, String message)  {

    }



}
