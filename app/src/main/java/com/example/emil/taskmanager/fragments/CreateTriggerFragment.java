package com.example.emil.taskmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.TriggerType;
import com.example.emil.taskmanager.entities.TriggerButton;
import com.example.emil.taskmanager.adapters.TriggerViewAdapter;
import com.example.emil.taskmanager.listeners.ITriggerButtonListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateTriggerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateTriggerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTriggerFragment extends Fragment implements ITriggerButtonListener, Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ITriggerButtonListener mListener;

    public CreateTriggerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTriggerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTriggerFragment newInstance(String param1, String param2) {
        CreateTriggerFragment fragment = new CreateTriggerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_trigger, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.TriggerGridView);
        List<TriggerButton> triggerButtonFragments = new ArrayList<>();

        triggerButtonFragments.add(new TriggerButton(R.drawable.alarm_icon, "Alarm",TriggerType.Alarm));
        triggerButtonFragments.add(new TriggerButton(R.drawable.date_icon, "Date",TriggerType.Date));

        TriggerViewAdapter adapter = new TriggerViewAdapter(inflater.getContext(),R.layout.fragment_trigger_button,triggerButtonFragments,this);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);

        // Inflate the layout for this fragment
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
        if (context instanceof ITriggerButtonListener) {
            mListener = (ITriggerButtonListener) context;
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
    public void triggerButtonPressed(TriggerType triggerType) {
        mListener.triggerButtonPressed(triggerType);
    }


}
