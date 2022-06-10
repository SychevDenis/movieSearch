package com.example.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.databinding.FragmentListFilmBinding
import com.example.moviesearch.modelAdapterRV.ModelItemRV

class FragmentListFilms : Fragment() {
    private lateinit var binding: FragmentListFilmBinding
    private val adapter = FilmsAdapter()
    private var presenter = PresenterFragmentList(this)
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
        init()
    }

    private fun init() {
        presenter.init()//инициализация presenter
        presenter.onClickListener() //установка с слушателя кликов
    }
    fun binding():FragmentListFilmBinding{
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
