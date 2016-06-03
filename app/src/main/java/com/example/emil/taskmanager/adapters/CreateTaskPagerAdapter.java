package com.example.emil.taskmanager.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.fragments.CreateTriggerFragment;
import com.example.emil.taskmanager.fragments.LoginFragment;
import com.example.emil.taskmanager.fragments.RegisterFragment;
import com.example.emil.taskmanager.fragments.TaskCreateFragment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 5/16/2016.
 */
public class CreateTaskPagerAdapter extends PagerAdapter implements Serializable {



    private final List<Fragment> fragments;
    private Context context;
    private  Task task;

    public CreateTaskPagerAdapter( Context context,Task task,List<Fragment> fragments) {
        this.task = task;
        this.fragments = fragments;
        this.context = context;

    }

    public void add(Fragment fragment){
        fragments.add(fragment);
    }

    public void remove(int position){
        fragments.remove(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        Fragment fragment = fragments.get(position);

        fragment.onAttach(context);
        fragment.onCreate(null);
        View view = fragment.onCreateView(inflater,container,null);

        container.addView(view);
        return view;
        //return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
