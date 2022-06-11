package com.example.moviesearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviesearch.databinding.FragmentListFilmBinding
import com.example.moviesearch.databinding.FragmentMovieInformationBinding
import com.example.moviesearch.mvp.model.Data
import com.squareup.picasso.Picasso

private const val keyBundle = "position"

class FragmentMovieInformation : Fragment() {
    private lateinit var binding: FragmentMovieInformationBinding
    private var params: Bundle = Bundle()
    private lateinit var presenter: PresenterFragmentMovieInformation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            params = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieInformationBinding.bind(view)
        presenter = PresenterFragmentMovieInformation(this, params)
    }

    fun binding(): FragmentMovieInformationBinding {
        return binding
    }

    companion object {
        fun newInstance(position: Int) =
            FragmentMovieInformation().apply {
                arguments = Bundle().apply {
                    putInt(keyBundle, position)
                }
            }
    }
}