package com.example.emil.taskmanager.adapters;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.utils.AnimationUtil;
import com.example.emil.taskmanager.utils.FragmentType;
import com.example.emil.taskmanager.listeners.ITaskViewListener;
import com.example.emil.taskmanager.fragments.IListFragment;
import com.example.emil.taskmanager.fragments.TaskFragment;
import com.example.emil.taskmanager.utils.PriorityColors;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 5/10/2016.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {

    private List<Task> fragments;
    private Context context;
    private ITaskViewListener taskView;

    public TaskListAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
        fragments = objects;
        this.context = context;
        taskView = (ITaskViewListener) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_task, parent, false);

            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(300);
            convertView.setAnimation(fadeIn);
        }

        //Create new Fragment
        final Task task = (Task) fragments.get(position);

        TextView triggerCount = (TextView) convertView.findViewById(R.id.Task_TriggerCount);
        triggerCount.setText(task.getTriggers().size() + " Trigger");

        TextView titleText = (TextView) convertView.findViewById(R.id.Task_Title);
        titleText.setText(task.getTitle());

        FrameLayout priorityBar = (FrameLayout) convertView.findViewById(R.id.Task_Priority_Indicator);
        priorityBar.setBackgroundColor(PriorityColors.getColor(task.getPriority()));

        ImageButton btnEdit = (ImageButton) convertView.findViewById(R.id.Task_Edit_Btn);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskView.EditTask(task);
            }
        });

        final RelativeLayout fragmentOverlay = (RelativeLayout) convertView.findViewById(R.id.Fragment_Overlay);

        fragmentOverlay.animate()
                .translationX(0)
                .setDuration(0)
                .start();


        fragmentOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task.isMenuOpen()){
                    AnimationUtil.swipeOpen(fragmentOverlay, context, 0);
                    task.setMenuOpen(false);
                }else {
                    taskView.ViewDetails(task);
                }
            }
        });

        fragmentOverlay.setOnTouchListener(handleTouch(task));

        ImageButton deleteBtn = (ImageButton) convertView.findViewById(R.id.Task_Delete_Btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskView.DeleteTask(task);
            }
        });




        return convertView;
    }



    @Override
    public void notifyDataSetChanged() {


        super.notifyDataSetChanged();
    }

    private float x1, x2;

    private View.OnTouchListener handleTouch(final Task task) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        RelativeLayout fragmentOverlay = (RelativeLayout) v.findViewById(R.id.Fragment_Overlay);
                        if (DetermineSwipeDirection(deltaX, fragmentOverlay,task))
                            return true;
                        break;
                }
                return false;
            }
        };
    }


    private final int MIN_DISTANCE = 20;

    private boolean DetermineSwipeDirection(float deltaX, RelativeLayout layout, Task task) {
        if (Math.abs(deltaX) > MIN_DISTANCE) {
            // Left to Right swipe action
            if (x2 > x1)
                AnimationUtil.swipeOpen(layout, context, 80);
            else
                AnimationUtil.swipeOpen(layout, context, -160);

            task.setMenuOpen(true);
            return true;
        }
        return false;
    }
}

