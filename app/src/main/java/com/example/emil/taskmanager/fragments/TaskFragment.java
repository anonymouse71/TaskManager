package com.example.emil.taskmanager.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.emil.taskmanager.FragmentType;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.activities.CreateTaskActivity;
import com.example.emil.taskmanager.activities.ITaskView;
import com.example.emil.taskmanager.activities.TaskViewActivity;
import com.example.emil.taskmanager.entities.Task;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements IListFragment {

    private final FragmentType FRAGMENT_TYPE = FragmentType.Task;

    private static final String TITLE = "param1";
    private static final String DESCRIPTION = "param2";

    private Task task;

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

    private ITaskView mListener;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param description Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String title, String description) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && task == null) {
            task = new Task();
            task.setTitle(getArguments().getString(TITLE));
            task.setDescription(getArguments().getString(DESCRIPTION));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_task, container, false);

        TextView titleText = (TextView) view.findViewById(R.id.Task_Title);
        titleText.setText(task.getTitle());

        Button btn = (Button) view.findViewById(R.id.EditTaskBtn);

        btn.setOnClickListener(editPressed());

        view.setTag(FRAGMENT_TYPE);
        // Inflate the layout for this fragment
        return view;
    }

    private View.OnClickListener editPressed(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.EditTask(task,position);
            }
        };
    }

    public void UpdateData(View view){

        TextView titleText = (TextView) view.findViewById(R.id.Task_Title);
        titleText.setText(task.getTitle());
    }

    // TODO: Rename method, update argument and hook method into UI event
/*    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ITaskView) {
            mListener = (ITaskView) context;
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
