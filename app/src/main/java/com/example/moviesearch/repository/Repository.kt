package com.example.weatherappcompose.data.repository

import android.util.Log
import com.example.moviesearch.modelPojo.pojoModel.Films
import com.example.weatherappcompose.data.api.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getListFilms(): Response<Films>? {
        var result: Response<Films>?
        try {
            result = RetrofitInstance.api.getListFilms()
            Log.d("Log","Ответ получен 'Repository'")

        } catch (e: Exception) {
            result = null
            Log.d("Log","Ошибка 'Repository' Watcher ${e.message}")
        }
        return result
    }
}