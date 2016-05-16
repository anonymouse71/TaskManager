package com.example.emil.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.emil.taskmanager.listeners.IStartScreenListener;
import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.adapters.StartPagePagerAdapter;

public class HomeActivity extends AppCompatActivity implements IStartScreenListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Button loginButton = (Button)findViewById(R.id.login_button);

        loginButton.setOnClickListener(loginClicked());*/

        viewPager = (ViewPager) findViewById(R.id.start_screen_viewPager);
        StartPagePagerAdapter adapter = new StartPagePagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(adapter);
    }

    private View.OnClickListener loginClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TaskViewActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loginPressed(String username, String password) {
        Intent intent = new Intent(this, TaskViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerPressed(String username, String password) {

    }

    @Override
    public void switchPage(int page) {
        viewPager.setCurrentItem(page);
    }
}
