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
import com.example.emil.taskmanager.utils.PriorityColors;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment  {

    private final FragmentType FRAGMENT_TYPE = FragmentType.Task;

    private static final String Task = "task";
    private static final String DESCRIPTION = "param2";

    private Task task;
    private Context ctx;


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

        return view;
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


}
