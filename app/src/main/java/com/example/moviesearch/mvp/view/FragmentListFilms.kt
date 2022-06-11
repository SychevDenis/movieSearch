package com.example.moviesearch.mvp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.FilmsAdapter
import com.example.moviesearch.OpenFragment
import com.example.moviesearch.mvp.presenter.PresenterFragmentList
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentListFilmBinding
import com.example.moviesearch.modelAdapterRV.ModelItemRV

class FragmentListFilms : Fragment() {
    lateinit var openFragment: OpenFragment
    private lateinit var binding: FragmentListFilmBinding
    private val adapter = FilmsAdapter()
    private lateinit var presenter : PresenterFragmentList

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenFragment) {//проверка на реализацию интерфейса у активити
            openFragment = context
        } else {
            throw RuntimeException("Activity must implement OpenFragmentMovieInformation")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListFilmBinding.bind(view)
        presenter = PresenterFragmentList(this)
    }

    fun binding(): FragmentListFilmBinding {
        return binding
    }

    fun listAdapter(list: ArrayList<ModelItemRV>) {
        adapter.listModelItemRV = list
    }

    fun getAdapterRV(): FilmsAdapter {
        return adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    companion object {
        fun newInstance() = FragmentListFilms()
    }
}
