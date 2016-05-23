package com.example.emil.taskmanager.api;

import com.example.emil.taskmanager.entities.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestTaskInterface {

    /**
     * Retrieves a list of all tasks in the database.
     * @return List<Task>.
     */
    @GET("task")
    Call<List<Task>> getTasks();

    /**
     * Retrieves a task from the database by ID.
     * @param id The ID of the task.
     * @return Task.
     */
    @GET("task/{id}")
    Call<Task> getTaskById(@Path("id") int id);

    /**
     * Edits a task in the database.
     * @param id The ID of the task.
     * @param task The updated task.
     * @return Task.
     */
    @PUT("task/{id}")
    Call<Task> editTask(@Path("id") int id, Task task);

    /**
     * Saves a task in the database.
     * @param task The Task to be created.
     * @return Task.
     */
    @POST("task")
    Call<Task> createTask(Task task);

    /**
     * Deletes a task in the database.
     * @param id The ID of the task.
     * @return Task.
     */
    @DELETE("task/{id}")
    Call<Task> deleteTask(@Path("id") int id);

}
