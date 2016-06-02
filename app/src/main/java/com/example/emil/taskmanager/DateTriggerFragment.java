package com.example.emil.taskmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.emil.taskmanager.entities.AlarmTrigger;
import com.example.emil.taskmanager.listeners.ITriggerButtonListener;
import com.example.emil.taskmanager.listeners.ITriggerListener;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateTriggerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DateTriggerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateTriggerFragment extends Fragment {
    private static final String TRIGGER = "trigger";

    private AlarmTrigger mTrigger;

    private ITriggerListener mListener;

    public DateTriggerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DateTriggerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateTriggerFragment newInstance(AlarmTrigger trigger) {
        DateTriggerFragment fragment = new DateTriggerFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_trigger, container, false);

        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.DateTrigger_DatePicker);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.DateTrigger_TimePicker);

        if (mTrigger.getDate() != null){
            Calendar cal = mTrigger.getDate();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            int hour=cal.get(Calendar.HOUR_OF_DAY);
            int min=cal.get(Calendar.MINUTE);

            datePicker.updateDate(year,month,day);
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(min);

        }

        Button btn = (Button) view.findViewById(R.id.DateTrigger_SaveBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year =  datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                calendar.set(Calendar.HOUR, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                mTrigger.setDate(calendar);
                mTrigger.setRepeating(false);
                mListener.saveTrigger(mTrigger);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ITriggerListener) {
            mListener = (ITriggerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ITriggerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
