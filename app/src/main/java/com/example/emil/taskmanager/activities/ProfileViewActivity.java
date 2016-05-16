package com.example.emil.taskmanager.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.fragments.ProfilePropertyFragment;

public class ProfileViewActivity extends AppCompatActivity {

    FragmentManager fm = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        fetchProfileDetails();
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

    public void fetchProfileDetails() {
        Fragment frag = fm.findFragmentById(R.id.profile_view_fragment_username);
        ;

    }
}
