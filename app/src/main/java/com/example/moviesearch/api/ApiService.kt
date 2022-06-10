package com.example.weatherappcompose.data.api

import com.example.moviesearch.modelPojo.pojoModel.Films
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("sequeniatesttask/films.json")
    suspend fun getListFilms():Response<Films>
}