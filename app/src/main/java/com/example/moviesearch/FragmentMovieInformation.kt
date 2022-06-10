package com.example.moviesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val position = "position"

class FragmentMovieInformation : Fragment() {
    lateinit var binding: FragmentMovieInformation
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_information, container, false)
    }

    companion object {

        fun newInstance(position: String) =
            FragmentMovieInformation().apply {
                arguments = Bundle().apply {
                    putString(position,param1)
                }
            }
    }
}