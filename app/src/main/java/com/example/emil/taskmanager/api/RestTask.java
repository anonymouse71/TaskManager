package com.example.emil.taskmanager.api;

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
}
