package com.example.moviesearch.modelAdapterRV

data class ModelFilm(
    val id:Int?=null,
    val movieCoverImageURL:String?=null,
    val localName: String?=null,
    val name: String?=null,
    val releaseYear: Int?=null,
    val rating: Double?=null,
    val genres: List<String>?=null,
    val description:String?=null,
    val type:Int?=null
){
    companion object {
        const val TYPE_GENRE_ENABLED = 100
        const val TYPE_GENRE_DISABLED = 101
        const val TYPE_FILM = 201
    }
}
