package com.example.emil.taskmanager.api;

import com.example.emil.taskmanager.dto.TaskDTO;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestTask {

    public RestTaskInterface service;

    public RestTask() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://tranquil-reef-74302.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestTaskInterface.class);
    }

    /**
     * Retrieves a list of all tasks in the database.
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

    /**
     * Retrieves a task from the database by ID.
     * @param id The ID of the task.
     * @return List<TaskDTO>
     */
    public TaskDTO getTaskById(int id) {

        Call<TaskDTO> task = service.getTaskById(id);
        Response<TaskDTO> response = null;
        try {
            response = task.execute();
            response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.body();

    }

    public Call<TaskDTO> editTask(int id, TaskDTO task) {
        return service.editTask(id, task);
    }

    /**
     * Saves a task in the database.
     * @param task The TaskDTO to be created.
     * @return TaskDTO
     */
    public void createTask(TaskDTO task) {

        Call<TaskDTO> newTask = service.createTask(task);

        try {
            Response<TaskDTO> response = newTask.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Call<TaskDTO> deleteTask(int id) {
        return service.deleteTask(id);
    }
}
