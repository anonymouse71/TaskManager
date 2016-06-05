package com.example.emil.taskmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.adapters.StartPagePagerAdapter;
import com.example.emil.taskmanager.api.RestClient;
import com.example.emil.taskmanager.dto.UserDTO;
import com.example.emil.taskmanager.listeners.IStartScreenListener;
import com.example.emil.taskmanager.utils.UserSettings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements IStartScreenListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager) findViewById(R.id.start_screen_viewPager);
        StartPagePagerAdapter adapter = new StartPagePagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
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

        UserSettings.hasSynchronized = false;

        final Activity context = this;

        UserDTO user = new UserDTO(username, password);
        RestClient rest = new RestClient();
        Call<UserDTO> call = rest.service.checkUser(user);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {

                    UserSettings.userId = response.body().getID();

                    Intent intent = new Intent(context, TaskViewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Invalid username/password", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(context, "Invalid username/password", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void registerPressed(String username, String password) {

        final Activity context = this;

        UserDTO user = new UserDTO(username, password);
        RestClient rest = new RestClient();
        Call<UserDTO> call = rest.service.addUser(user);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(context, "YOU IS REGISTER LAL", Toast.LENGTH_LONG).show();
                    viewPager.setCurrentItem(0);

                } else {
                    Toast.makeText(context, "Couldn't register. Please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(context, "Error connecting to server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void switchPage(int page) {
        viewPager.setCurrentItem(page);
    }
}
