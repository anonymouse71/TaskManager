package com.example.emil.taskmanager.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.example.emil.taskmanager.NotificationService;
import com.example.emil.taskmanager.R;

/**
 * Created by Administrator on 5/23/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getExtras().getString("Title");
        String message = intent.getExtras().getString("Message");
        long taskId = intent.getExtras().getLong("Id");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo_sm)
                        .setContentTitle(title)
                        .setContentText(message);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, TaskDetailsActivity.class);

        resultIntent.putExtra("Id",taskId);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(TaskViewActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify((int)taskId , mBuilder.build());
    }
}
