package com.example.emil.taskmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.Task;
import com.example.emil.taskmanager.listeners.ICreateTaskListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskCreateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TASK = "task";

    // TODO: Rename and change types of parameters
    private Task mTask;

    private ICreateTaskListener mListener;
    private TextView titleText;
    private TextView descriptionText;

    public TaskCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param description Parameter 2.
     * @return A new instance of fragment TaskCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskCreateFragment newInstance(Task task) {
        TaskCreateFragment fragment = new TaskCreateFragment();
        Bundle args = new Bundle();
        args.putSerializable(TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTask = (Task) getArguments().getSerializable(TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_create, container, false);


        titleText = (TextView) view.findViewById(R.id.CreateTask_Title);
        titleText.setText(mTask.getTitle());

        descriptionText = (TextView) view.findViewById(R.id.CreateTask_Description);
        descriptionText.setText(mTask.getDescription());

        Button saveBtn = (Button) view.findViewById(R.id.CreateTask_SaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String description = descriptionText.getText().toString();

                mListener.savePressed(title,description);
            }
        });

        // Inflate the layout for this fragment
        return  view;
    }

    public void savePressed() {
        if (mListener != null) {
            String title = titleText.getText().toString();
            String description = descriptionText.getText().toString();

            mListener.savePressed(title,description);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ICreateTaskListener) {
            mListener = (ICreateTaskListener) context;
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
