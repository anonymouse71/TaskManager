package com.example.emil.taskmanager.api;

import com.example.emil.taskmanager.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface RestUserInterface {

    /**
     * Checks if the username and password matches
     * @return User Returns user object if it's a match.
     */
    @POST
    Call<List<UserDTO>> checkUser();

}
