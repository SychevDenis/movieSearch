package com.example.moviesearch.modelAdapterRV

data class ModelGenre(
    val nameGenre: String?=null,
    val type:Int?=null
){
    companion object {
        const val TYPE_GENRE_ENABLED = 100
        const val TYPE_GENRE_DISABLED = 101
        const val TYPE_FILM = 201
    }
}
