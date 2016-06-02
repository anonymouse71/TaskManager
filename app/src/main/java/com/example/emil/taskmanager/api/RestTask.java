package com.example.emil.taskmanager.api;

import com.example.emil.taskmanager.dto.TaskDTO;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestTask {

    public RestInterface service;

    public RestTask() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://tranquil-reef-74302.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestInterface.class);
    }

    /**
     * Retrieves a list of all tasks in the database.
     *
     * @return List<TaskDTO>
     */
    public List<TaskDTO> getTasks() {

        Call<List<TaskDTO>> taskList = service.getTasks();
        Response<List<TaskDTO>> response = null;

        try {
            response = taskList.execute();
            response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.body();

    }

}
