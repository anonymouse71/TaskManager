package com.example.emil.taskmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.emil.taskmanager.utils.AnimationUtil;
import com.example.emil.taskmanager.utils.FragmentType;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.listeners.ITaskViewListener;
import com.example.emil.taskmanager.entities.Task;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements IListFragment<Task> {

    private final FragmentType FRAGMENT_TYPE = FragmentType.Task;

    private static final String Task = "task";
    private static final String DESCRIPTION = "param2";

    private Task task;
    private Context ctx;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;

    private ITaskViewListener mListener;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title       Parameter 1.
     * @param description Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(Task task) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(Task, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && task == null) {
            task = (Task) getArguments().getSerializable(Task);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        ImageButton btnEdit = (ImageButton) view.findViewById(R.id.Task_Edit_Btn);
        btnEdit.setOnClickListener(editPressed());

        FrameLayout btnDetails = (FrameLayout) view.findViewById(R.id.TaskFragmentMain);
        btnDetails.setOnClickListener(detailsPressed());
        view.setOnTouchListener(handleTouch());

        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.Task_Delete_Btn);
        deleteBtn.setOnClickListener(deletePressed());

        updateGUI(view);

        view.setTag(FRAGMENT_TYPE);
        // Inflate the layout for this fragment
        return view;
    }



    private float x1, x2;

    private View.OnTouchListener handleTouch() {
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
                        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.Fragment_Overlay);

                        if (DetermineSwipeDirection(deltaX, layout))
                            return true;
                        break;
                }
                return false;
            }
        };
    }


    private final int MIN_DISTANCE = 20;

    private boolean DetermineSwipeDirection(float deltaX, RelativeLayout layout) {
        if (Math.abs(deltaX) > MIN_DISTANCE) {
            // Left to Right swipe action
            if (x2 > x1)
                AnimationUtil.swipeOpen(layout, ctx, 0);
            else
                AnimationUtil.swipeOpen(layout, ctx, -80);

            return true;
        }
        return false;
    }

    private View.OnClickListener editPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.EditTask(task);
            }
        };
    }

    private View.OnClickListener deletePressed() {
        final IListFragment self = this;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.DeleteTask(self, task);
            }
        };
    }


    private View.OnClickListener detailsPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ViewDetails(task);

            }
        };
    }

    private void updateGUI(View view) {
        TextView titleText = (TextView) view.findViewById(R.id.Task_Title);
        titleText.setText(task.getTitle());
    }

    @Override
    public void updateData(View view,Task data) {

        task = data;
        updateGUI(view);
    }

    public void UpdateData(View view) {

        updateGUI(view);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.Fragment_Overlay);

        if (ctx != null)
        AnimationUtil.swipeOpen(layout, ctx, 0);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
        if (context instanceof ITaskViewListener) {
            mListener = (ITaskViewListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public FragmentType getFragmentType() {
        return FRAGMENT_TYPE;
    }

}
