package com.example.moviesearch.mvp.presenter

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.moviesearch.R
import com.example.moviesearch.mvp.model.Data
import com.example.moviesearch.mvp.view.FragmentMovieInformation
import com.squareup.picasso.Picasso

class PresenterFragmentMovieInformation(
    context: FragmentMovieInformation,
    params: Bundle
) {
    private val position = params.getInt("position")
    private val view = context
    private val model = Data
    private val binding = view.binding()

    init {
        setBindingFragmentMovieInformation()
    }

    @SuppressLint("SetTextI18n")
    private fun setBindingFragmentMovieInformation() {//заполнить view объекты
        val model = model.listModelItemRVSort[position]
        Picasso.get().load(model.modelFilm?.movieCoverImageURL)
            .placeholder(R.drawable.image_not_found)
            .into(binding.IVFilmMovieInformation)//загрузка фото из сети в IV
        binding.TVDescriptionMovieInformation.text =
            model.modelFilm?.description
        binding.TVRatingMovieInformation.text =
            "Рейтинг:  ${model.modelFilm?.rating.toString()}"
        binding.TVReleaseYearMovieInformation.text =
            "Год:  ${model.modelFilm?.releaseYear.toString()}"
        binding.TVLocalNameMovieInformation.text =
            model.modelFilm?.name
    }

}