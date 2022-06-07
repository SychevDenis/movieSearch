package com.example.moviesearch.modelPojo.pojoModel


import com.google.gson.annotations.SerializedName

data class Films(
    @SerializedName("films")
    val films: List<Film>
)