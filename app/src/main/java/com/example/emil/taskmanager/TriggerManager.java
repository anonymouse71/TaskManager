package com.example.emil.taskmanager;

/**
 * Created by Administrator on 5/23/2016.
 */
public class TriggerManager {
    private static TriggerManager   _instance;

    private TriggerManager()
    {

    }

    public synchronized static TriggerManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new TriggerManager();
        }
        return _instance;
    }
}
