package com.example.emil.taskmanager.fragments;

import android.view.View;

import com.example.emil.taskmanager.utils.FragmentType;

/**
 * Created by Administrator on 5/11/2016.
 */
public  interface  IListFragment<T> {
    FragmentType getFragmentType();

    void updateData(View view,T data);
}
