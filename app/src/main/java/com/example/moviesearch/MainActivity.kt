package com.example.moviesearch

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OpenFragment {
    private lateinit var binding: ActivityMainBinding
    private var paramPosition: Int? = null
    private var paramType: Int? = null
    private var title:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list_film)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(paramPosition, paramType,title)
    }

    override fun openFragment(position: Int?, fragmentType: Int?, titleActionBar:String?) {//какой фрагмент открыть
        paramType = fragmentType
        paramPosition = position
        when (fragmentType) {
            null -> {
                supportActionBar?.let {
                    it.setBackgroundDrawable(ColorDrawable(Color.parseColor("#323232")))
                    it.title = "Главная"
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FragmentListFilms.newInstance())
                    .commit()
            }
            TYPE_FRAGMENT_MOVIE_INFORMATION -> {
                supportActionBar?.let {
                    it.setBackgroundDrawable(ColorDrawable(Color.parseColor("#323232")))
                    it.title = titleActionBar
                }
                position?.let {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, FragmentMovieInformation.newInstance(it))
                        .commit()
                }
            }
            TYPE_FRAGMENT_LIST_FILMS -> {
                supportActionBar?.let {
                    it.setBackgroundDrawable(ColorDrawable(Color.parseColor("#323232")))
                    it.title = "Главная"
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FragmentListFilms.newInstance())
                    .commit()
            }
            else -> {
                throw RuntimeException("Unknown fragment type")
            }
        }
    }

    companion object {
        const val TYPE_FRAGMENT_LIST_FILMS = 400
        const val TYPE_FRAGMENT_MOVIE_INFORMATION = 401

    }
}