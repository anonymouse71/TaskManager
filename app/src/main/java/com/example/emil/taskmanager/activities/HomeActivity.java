package com.example.emil.taskmanager.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.emil.taskmanager.R;
import com.example.emil.taskmanager.adapters.StartPagePagerAdapter;
import com.example.emil.taskmanager.api.RestClient;
import com.example.emil.taskmanager.dto.UserDTO;
import com.example.emil.taskmanager.listeners.IStartScreenListener;
import com.example.emil.taskmanager.utils.UserSettings;
import com.example.emil.taskmanager.utils.Validator;

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
        boolean isUsernameValid = true;
        boolean isPasswordValid = true;
        final ProgressDialog loadingDialog = new ProgressDialog(context);

        // Show loading dialog
        loadingDialog.setMessage("Logging in");
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        // Close the keyboard
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // Keyboard is not active.
        }

        // Validate fields
        EditText un = (EditText) findViewById(R.id.login_activity_username);
        EditText pw = (EditText) findViewById(R.id.login_activity_password);

        // Check username
        if (!Validator.isPresent(un.getText().toString())) {
            un.setError("This field cannot be empty");
            isUsernameValid = false;
        }

        if (isUsernameValid && !Validator.isEmail(un.getText().toString())) {
            un.setError("Please provide a valid email");
            isUsernameValid = false;
        }

        // Check password
        if (!Validator.isPresent(pw.getText().toString())) {
            pw.setError("This field cannot be empty");
            isPasswordValid = false;
        }

        if (isPasswordValid && isUsernameValid) {

            UserDTO user = new UserDTO(username, password);
            RestClient rest = new RestClient();
            Call<UserDTO> call = rest.service.checkUser(user);
            call.enqueue(new Callback<UserDTO>() {

                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {

                    loadingDialog.dismiss();

                    if (response.isSuccessful()) {
                        UserSettings.userId = response.body().getID();
                        Intent intent = new Intent(context, TaskViewActivity.class);
                        startActivity(intent);


                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Login failed")
                                .setMessage("Your username or password is incorrect.")
                                .setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing.
                                    }
                                })
                                .setIcon(R.drawable.ic_alert)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {
                    loadingDialog.dismiss();

                    new AlertDialog.Builder(context)
                            .setTitle("Login failed")
                            .setMessage("Your username or password is incorrect.")
                            .setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing.
                                }
                            })
                            .setIcon(R.drawable.ic_alert)
                            .show();
                }
            });

        } else {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void registerPressed(String username, String password) {

        final Activity context = this;
        boolean isUsernameValid = true;
        boolean isPasswordValid = true;
        final ProgressDialog loadingDialog = new ProgressDialog(context);

        // Show loading dialog
        loadingDialog.setMessage("Registering");
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        // Close the keyboard
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // Keyboard is not active.
        }

        // Validate fields
        EditText un = (EditText) findViewById(R.id.register_activity_email);
        EditText pw = (EditText) findViewById(R.id.register_activity_password);

        // Check username
        if (!Validator.isPresent(un.getText().toString())) {
            un.setError("This field cannot be empty");
            isUsernameValid = false;
        }

        if (isUsernameValid && Validator.isBelow(un.getText().toString(), 6)) {
            un.setError("Your email must be at least 6 characters");
            isUsernameValid = false;
        }

        if (isUsernameValid && !Validator.isEmail(un.getText().toString())) {
            un.setError("Please provide a valid email");
            isUsernameValid = false;
        }

        // Check password
        if (!Validator.isPresent(pw.getText().toString())) {
            pw.setError("This field cannot be empty");
            isPasswordValid = false;
        }

        if (isPasswordValid && !Validator.isPassword(pw.getText().toString())) {
            pw.setError("Your password must be at least 8 characters and contain at least 1 letter and 1 number");
            isPasswordValid = false;
        }

        if (isUsernameValid && isPasswordValid)
        {
        UserDTO user = new UserDTO(username, password);
        RestClient rest = new RestClient();
        Call<UserDTO> call = rest.service.addUser(user);
        call.enqueue(new Callback<UserDTO>() {

            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {

                loadingDialog.dismiss();

                if (response.isSuccessful()) {

                    new AlertDialog.Builder(context)
                            .setTitle("Success")
                            .setMessage("You are registered. You can now log in.")
                            .setPositiveButton("AWESOME", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    viewPager.setCurrentItem(0);
                                }
                            })
                            .setIcon(R.drawable.ic_success)
                            .show();

                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("An Error Occurred")
                            .setMessage("We could not create your user. Please try again.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing.
                                }
                            })
                            .setIcon(R.drawable.ic_alert)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                new AlertDialog.Builder(context)
                        .setTitle("An Error Occurred")
                        .setMessage("We could not connect to the server. Please try again.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing.
                            }
                        })
                        .setIcon(R.drawable.ic_alert)
                        .show();
            }

        });
    }

    else

    {
        loadingDialog.dismiss();
    }

}

    @Override
    public void switchPage(int page) {
        viewPager.setCurrentItem(page);
    }
}
