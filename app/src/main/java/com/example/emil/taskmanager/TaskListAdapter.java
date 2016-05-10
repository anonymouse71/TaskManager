package com.example.emil.taskmanager;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 5/10/2016.
 */
public class TaskListAdapter extends ArrayAdapter<IListFragment> {

    private  List<IListFragment> fragments;

    public TaskListAdapter(Context context, int resource, List<IListFragment> objects) {
        super(context, resource, objects);
        fragments = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;

        switch (fragments.get(position).getLayoutId()){
            case R.layout.fragment_task:
                rowView = inflater.inflate(R.layout.fragment_task, parent, false);
                break;
            case R.layout.fragment_seperator:
                rowView = inflater.inflate(R.layout.fragment_seperator, parent, false);
                break;
        }

        //TextView textView = (TextView) rowView.findViewById(R.id.Task_Title);


        return rowView;

    }


}
