package com.example.emil.taskmanager.api;

import com.example.emil.taskmanager.dto.TaskDTO;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestTask {

    private RestTaskInterface service;

    public RestTask() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://tranquil-reef-74302.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestTaskInterface.class);
    }

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

    public Call<TaskDTO> getTaskById(int id) {
        return service.getTaskById(id);
    }

    public Call<TaskDTO> editTask(int id, TaskDTO task) {
        return service.editTask(id, task);
    }

    public Call<TaskDTO> createTask(TaskDTO task) {
        return service.createTask(task);
    }

    public Call<TaskDTO> deleteTask(int id) {
        return service.deleteTask(id);
    }
}
