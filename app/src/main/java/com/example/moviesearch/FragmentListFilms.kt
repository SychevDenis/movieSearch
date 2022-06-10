package com.example.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.moviesearch.FilmsAdapter.Companion.TYPE_FILM

import com.example.moviesearch.FilmsAdapter.Companion.TYPE_GENRE_DISABLED
import com.example.moviesearch.FilmsAdapter.Companion.TYPE_GENRE_ENABLED
import com.example.moviesearch.FilmsAdapter.Companion.TYPE_LABEL
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

        binding.apply { //настройка LayoutManager у RV
            RWListFilms.layoutManager = GridLayoutManager(view?.context, 2).apply {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (adapter.getItemViewType(position)) {
                            TYPE_FILM -> 1
                            else -> 2
                        }
                    }
                }
            }
            RWListFilms.adapter = adapter
        }

        presenter.onClickListener() //установка с слушателя кликов
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
