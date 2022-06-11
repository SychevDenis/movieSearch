package com.example.moviesearch.mvp.presenter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.moviesearch.R
import com.example.moviesearch.mvp.view.FragmentListFilms
import com.example.moviesearch.mvp.view.FragmentMovieInformation
import com.example.moviesearch.mvp.view.MainActivity

class PresenterActivity(context: MainActivity) {
    private val view = context
    fun readSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val param = savedInstanceState.getInt("KEY_TYPE", MainActivity.TYPE_FRAGMENT_LIST_FILMS)
            view.setParamType(param)
            val title = savedInstanceState.getString("KEY_TITLE", view.getDefaultTitle())
            view.setTitle(title)
        }
    }

    fun openFragment(
        position: Int?, fragmentType: Int, titleActionBar: String,
        supportFragmentManager: FragmentManager,
        supportActionBar: androidx.appcompat.app.ActionBar?
    ) {
        when (fragmentType) {
            MainActivity.TYPE_FRAGMENT_MOVIE_INFORMATION -> {
                editActionBar(supportActionBar, titleActionBar)
                position?.let {
                    supportFragmentManagerMovieInformation(supportFragmentManager, it)
                }
                view.setParamType(fragmentType)
                view.setTitle(titleActionBar)
            }
            MainActivity.TYPE_FRAGMENT_LIST_FILMS -> {
                editActionBar(supportActionBar, view.getDefaultTitle())
                supportFragmentManagerListFilms(supportFragmentManager)
                view.setParamType(fragmentType)
                view.setTitle(titleActionBar)
            }
            else -> {
                throw RuntimeException("Unknown fragment type")
            }
        }
    }

    private fun supportFragmentManagerListFilms(supportFragmentManager: FragmentManager) {//настройка FragmentManagerListFilms
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentListFilms.newInstance())
            .commit()
    }

    private fun supportFragmentManagerMovieInformation(//настройка FragmentMovieInformation
        supportFragmentManager: FragmentManager,
        position: Int
    ) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, FragmentMovieInformation.newInstance(position))
            .addToBackStack(null)
            .commit()
    }

    private fun editActionBar( //настройка экшен бара
        supportActionBar: androidx.appcompat.app.ActionBar?,
        titleActionBar: String
    ) {
        supportActionBar?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.parseColor("#323232")))
            it.title = titleActionBar
        }
    }

    fun backPress(
        supportActionBar: androidx.appcompat.app.ActionBar?,
        titleActionBar: String
    ) {
        editActionBar(supportActionBar, titleActionBar)
    }
}