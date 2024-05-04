package com.example.semana06.service;

import com.example.semana06.entity.libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceLibro {

    @POST("servicio/libro")
    public Call<libro> registraLibro(@Body libro objLibro);
}
