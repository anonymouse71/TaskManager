package com.example.emil.taskmanager;

import android.content.BroadcastReceiver;
import android.os.Bundle;

/**
 * Created by Administrator on 5/23/2016.
 */
public interface ITrigger {
    Class<? extends BroadcastReceiver> getReceiver();

    void startTrigger(Bundle data);
}
