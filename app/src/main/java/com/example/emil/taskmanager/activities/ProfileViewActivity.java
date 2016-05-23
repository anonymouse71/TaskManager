package com.example.emil.taskmanager.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.fragments.ProfilePropertyFragment;

import java.util.List;

public class ProfileViewActivity extends AppCompatActivity {

    FragmentManager fm = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout layout = (LinearLayout) findViewById(R.id.profile_listView);

        Fragment fragment = (Fragment)ProfilePropertyFragment.newInstance("Username", "Lars");


        fragment.onCreate(null);

        layout.addView(fragment.onCreateView(inflater,layout,null));

    }

    /**
     * Inflates the menu and adds items to the action bar.
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_view, menu);
        return true;
    }

    /**
     * Handles action bar item clicks.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile_edit) {
            Intent intent = new Intent(this, ProfileViewActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
