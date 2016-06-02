package com.example.emil.taskmanager.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.entities.AlarmTrigger;
import com.example.emil.taskmanager.listeners.ITriggerListener;

import java.io.Serializable;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmTriggerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmTriggerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmTriggerFragment extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String TRIGGER = "trigger";

    private AlarmTrigger mTrigger;

    private ITriggerListener mListener;

    public AlarmTriggerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hour Parameter 1.
     * @param minute Parameter 2.
     * @return A new instance of fragment AlarmTriggerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmTriggerFragment newInstance(AlarmTrigger trigger) {
        AlarmTriggerFragment fragment = new AlarmTriggerFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRIGGER, trigger);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTrigger = (AlarmTrigger) getArguments().getSerializable(TRIGGER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_trigger, container, false);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.TriggerTimePicker);

        if (mTrigger.getDate() != null){

            timePicker.setCurrentHour(mTrigger.getDate().get(Calendar.HOUR));
            timePicker.setCurrentMinute(mTrigger.getDate().get(Calendar.MINUTE));
        }

        Button btn = (Button) view.findViewById(R.id.AlarmTrigger_SaveBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                mTrigger.setDate(calendar);
                mTrigger.setRepeating(true);
                mListener.saveTrigger(mTrigger);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
           // mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ITriggerListener) {
            mListener = (ITriggerListener) context;
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
