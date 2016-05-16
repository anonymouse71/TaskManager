package com.example.emil.taskmanager.listeners;

/**
 * Created by Administrator on 5/15/2016.
 */
public interface IStartScreenListener {

    void loginPressed(String username,String password);

    void registerPressed(String username,String password);

    void switchPage(int page);

}
