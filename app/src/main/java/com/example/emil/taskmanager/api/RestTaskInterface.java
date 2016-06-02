package com.example.emil.taskmanager.api;

import com.example.emil.taskmanager.dto.TaskDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestTaskInterface {

    /**
     * Retrieves a list of all tasks in the database.
     * @return List<TaskDTO>.
     */
    @GET("tasks")
    Call<List<TaskDTO>> getTasks();

    /**
     * Retrieves a task from the database by ID.
     * @param id The ID of the task.
     * @return TaskDTO.
     */
    @GET("tasks/{id}")
    Call<TaskDTO> getTaskById(@Path("id") int id);

    /**
     * Edits a task in the database.
     * @param id The ID of the task.
     * @param task The updated task.
     * @return TaskDTO.
     */
    @PUT("tasks/{id}")
    Call<TaskDTO> editTask(@Path("id") String id, TaskDTO task);

    /**
     * Saves a task in the database.
     * @param task The TaskDTO to be created.
     * @return TaskDTO.
     */
    @POST("tasks")
    Call<TaskDTO> createTask(@Body TaskDTO task);

    /**
     * Deletes a task in the database.
     * @param id The ID of the task.
     * @return TaskDTO.
     */
    @DELETE("tasks/{id}")
    Call<TaskDTO> deleteTask(@Path("id") String id);

}
