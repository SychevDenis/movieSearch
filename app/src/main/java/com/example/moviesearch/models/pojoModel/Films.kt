package com.example.moviesearch.models.pojoModel


import com.google.gson.annotations.SerializedName

data class Films(
    @SerializedName("films")
    val films: List<Film>
)