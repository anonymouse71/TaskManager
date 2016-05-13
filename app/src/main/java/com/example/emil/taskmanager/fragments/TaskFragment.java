package com.example.emil.taskmanager.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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

    private ITaskView mListener;

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

        UpdateGUI(view);

        view.setTag(FRAGMENT_TYPE);
        // Inflate the layout for this fragment
        return view;
    }

    private void UpdateGUI(View view) {
        TextView titleText = (TextView) view.findViewById(R.id.Task_Title);
        titleText.setText(task.getTitle());
    }

    private boolean IsDeleting;
    private float x1,x2;
    private final int MIN_DISTANCE = 20;

    private View.OnTouchListener handleTouch(){
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;
                        if (Math.abs(deltaX) > MIN_DISTANCE)
                        {
                            // Left to Right swipe action
                            if (x2 > x1 && IsDeleting)
                            {
                                RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.Fragment_Overlay);

                                layout.animate()
                                        .translationX(convertDpToPixel(0, ctx))
                                        .setDuration(300)
                                        .start();

                                IsDeleting = false;
                                return true;
                            }

                            // Right to left swipe action
                            else if (x1 > x2 && !IsDeleting)
                            {
                                RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.Fragment_Overlay);

                                layout.animate()
                                        .translationX(convertDpToPixel(-80, ctx))
                                        .setDuration(300)
                                        .start();

                                IsDeleting = true;
                                return true;
                            }
                        }
                        else
                        {
                            // consider as something else - a screen tap for example
                        }
                        break;
                }
                return false;
            }
        };
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

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    public void updateData(Task data) {
        task = data;
    }

    public void UpdateData(View view) {

        UpdateGUI(view);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.Fragment_Overlay);

        if (ctx != null){
            layout.animate()
                    .translationX(convertDpToPixel(0, ctx))
                    .setDuration(0)
                    .start();
            IsDeleting = false;

        }
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
        ctx = context;
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
