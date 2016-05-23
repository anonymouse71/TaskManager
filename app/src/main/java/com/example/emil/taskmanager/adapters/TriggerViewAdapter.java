package com.example.emil.taskmanager.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.TriggerButtonFragment;
import com.example.emil.taskmanager.listeners.ITriggerButtonListener;

import java.util.List;

/**
 * Created by Administrator on 5/18/2016.
 */
public class TriggerViewAdapter extends ArrayAdapter<TriggerButton> {
    private final ITriggerButtonListener listener;
    private Context context;

    public TriggerViewAdapter(Context context, int resource, List<TriggerButton> objects , ITriggerButtonListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Create new Fragment
        TriggerButton button = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_trigger_button, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.TriggerButton_text);
        textView.setText(button.getText());

        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.TriggerButton_Btn);
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), button.getImageId(), null);
        imageButton.setImageDrawable(drawable);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Click();
            }
        });

        return convertView;
    }


}
