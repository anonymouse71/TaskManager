package com.example.emil.taskmanager;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;

import com.example.emil.taskmanager.activities.ITaskView;
import com.example.emil.taskmanager.fragments.IListFragment;
import com.example.emil.taskmanager.fragments.TaskFragment;

import java.util.List;

/**
 * Created by Administrator on 5/10/2016.
 */
public class TaskListAdapter extends ArrayAdapter<IListFragment> {

    private List<IListFragment> fragments;
    private  Context context;
private ITaskView taskView;

    public TaskListAdapter(Context context, int resource, List<IListFragment> objects) {
        super(context, resource, objects);
        fragments = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Reuse View if possible
        if (convertView != null && canConvertView(position, convertView)) {
            return convertView;
        }

        //Create new Fragment
        Fragment fragment = (Fragment) fragments.get(position);

        fragment.onCreate(null);
        View fragmentView = fragment.onCreateView(inflater, parent, null);
fragment.onAttach(context);
        ((IListFragment)fragment).setPosition(position);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        fragmentView.setAnimation(fadeIn);

        return fragmentView;
    }

    /**
     * Returns true if View can be reused, false otherwise
     * @param position
     * @param convertView
     * @return
     */
    private boolean canConvertView(int position, View convertView) {
        //Determine View type
        FragmentType type = (FragmentType) convertView.getTag();
        IListFragment iListFragment = fragments.get(position);

        //If old View and new View is same type
        if (iListFragment.getFragmentType() == type) {
            switch (type) {
                case Task:
                    TaskFragment taskFragment = (TaskFragment) iListFragment;
                    taskFragment.onCreate(null);
                    taskFragment.setPosition(position);
                    taskFragment.UpdateData(convertView);
                    return true;
                case Seperator:
                    return true;
            }
        }
        return false;
    }

}

