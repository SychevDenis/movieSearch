package com.example.moviesearch

import android.os.Bundle
import com.example.moviesearch.mvp.model.Data
import com.squareup.picasso.Picasso

class PresenterFragmentMovieInformation(
    context: FragmentMovieInformation,
    private val params: Bundle
) {
    private val view = context
    private val model = Data

    init {
        setBindingFragmentMovieInformation()
    }

    private fun setBindingFragmentMovieInformation() {//заполнить view объекты
        val binding = view.binding()
        val position = params.getInt("position")
        Picasso.get().load(model.listModelItemRV[position].modelFilm?.movieCoverImageURL)
            .placeholder(R.drawable.image_not_found)
            .into(binding.IVFilmMovieInformation)//загрузка фото из сети в IV
        binding.TVDescriptionMovieInformation.text =
            model.listModelItemRV[position].modelFilm?.description
        binding.TVRatingMovieInformation.text =
            "Рейтинг:  ${model.listModelItemRV[position].modelFilm?.rating.toString()}"
        binding.TVReleaseYearMovieInformation.text =
            "Год:  ${model.listModelItemRV[position].modelFilm?.releaseYear.toString()}"
        binding.TVLocalNameMovieInformation.text =
            model.listModelItemRV[position].modelFilm?.name

    }
}