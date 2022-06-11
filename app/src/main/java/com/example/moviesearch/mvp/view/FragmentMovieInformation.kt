package com.example.moviesearch.mvp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesearch.OpenFragment
import com.example.moviesearch.mvp.presenter.PresenterFragmentMovieInformation
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentMovieInformationBinding

private const val keyBundle = "position"

class FragmentMovieInformation : Fragment() {
    private lateinit var binding: FragmentMovieInformationBinding
    private var params: Bundle = Bundle()
    private lateinit var presenter: PresenterFragmentMovieInformation
    lateinit var openFragment: OpenFragment
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