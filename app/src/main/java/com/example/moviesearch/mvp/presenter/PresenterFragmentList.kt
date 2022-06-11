package com.example.moviesearch.mvp.presenter

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesearch.ConvertorPojoToAdapterRV
import com.example.moviesearch.FilmsAdapter
import com.example.moviesearch.modelAdapterRV.ModelFilm
import com.example.moviesearch.modelAdapterRV.ModelGenre
import com.example.moviesearch.modelAdapterRV.ModelItemRV
import com.example.moviesearch.modelAdapterRV.ModelLabel
import com.example.moviesearch.modelPojo.pojoModel.Films
import com.example.moviesearch.mvp.model.Data
import com.example.moviesearch.mvp.view.FragmentListFilms
import com.example.weatherappcompose.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class PresenterFragmentList(context: FragmentListFilms) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val scopeMain = CoroutineScope(Dispatchers.Main)
    private val repository = Repository()
    private val model = Data
    private var view = context

    init {
        scope.launch {//выполнить не в главном потоке
            val resultApi = getResponseApiToSetData()//выполнить api запрос
            val convertResult =
                resultApi?.let { ConvertorPojoToAdapterRV(it).result() } //конвертируем данные в понятные для адаптера RV
            setDataList(convertResult)//сохрание данных в Data
            setDataListSort(convertResult)//сохрание данных в Data.sort

            scopeMain.launch {//выполнить в главном потоке
                val dataList = getDataListSort()//чтение данных
                setBindingFragmentListFilms()//настаройка биндинга у FragmentListFilms
                setViewAdapterRV(dataList)//отправка данных в адаптер
            }
        }
        onClickListener()//установка слушателя
    }

    private suspend fun getResponseApiToSetData(): Films? {
        if (getDataList().isEmpty()) {
            //если объект пустой, то выполняем api запрос
            val result = repository.getListFilms()?.body()
            result?.let {
                return it
            }
        }
        return null
    }

    private fun setBindingFragmentListFilms() {
        val binding = view.binding()
        binding.apply {
            RWListFilms.layoutManager = GridLayoutManager(view.context, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (getViewAdapterRV().getItemViewType(position)) {
                            FilmsAdapter.TYPE_FILM -> 1
                            else -> 2
                        }
                    }
                }
            }
            RWListFilms.adapter = getViewAdapterRV()
        }
    }

    private fun onClickListener() { //при клике на объект в RV
        getViewAdapterRV().onModelItemRVClickListener = { modelItemRV, position ->
            getDataList().let {
                var type = 0
                if (getType(modelItemRV) == TYPE_GENRE_DISABLED) {
                    type = onClickGenreDisabled(it, position)
                } else if (getType(modelItemRV) == TYPE_GENRE_ENABLED) {
                    type = onClickGenreEnable(it, position)
                } else if (getType(modelItemRV) == TYPE_FILM) {
                    type = TYPE_FILM
                    openFragment(position)
                    Log.i("Log", position.toString())
                }
                val sort = sortListByGenre(position, type)
                setViewAdapterRV(sort)//вывести данные в адаптер RV
            }
        }
    }

    private fun openFragment(position: Int) {
        var title = ""
        model.listModelItemRVSort[position].modelFilm?.localName?.let {title= it }
        view.openFragment.openFragment(
            position,
            TYPE_FRAGMENT_MOVIE_INFORMATION,
            title
        )
    }

    private fun onClickGenreEnable(it: ArrayList<ModelItemRV>, position: Int): Int {
        it[position].modelGenre?.type =
            ModelGenre.TYPE_GENRE_DISABLED  //внести изменения в Data
        return TYPE_GENRE_ENABLED
    }

    private fun onClickGenreDisabled(it: ArrayList<ModelItemRV>, position: Int): Int {
        var countEnable = 0//количество GENRE_ENABLED
        for (modelItem in it) {//подсчет всех GENRE_ENABLED в Data
            if (getType(modelItem) == TYPE_GENRE_ENABLED) {
                countEnable++
            }
        }
        if (countEnable < 1) {//если их менее 1, то
            it[position].modelGenre?.type = ModelGenre.TYPE_GENRE_ENABLED //внести изменения в Data
        } else {
            for (modelItem in it) {//найти все GENRE_ENABLED и переписать их в GENRE_DISABLED
                if (getType(modelItem) == TYPE_GENRE_ENABLED) {
                    modelItem.modelGenre?.type = TYPE_GENRE_DISABLED
                }
            }
            it[position].modelGenre?.type = ModelGenre.TYPE_GENRE_ENABLED//внести изменения в Data
        }
        return TYPE_GENRE_DISABLED
    }


    private fun getType(model: ModelItemRV): Int {//определение типа объекта из RV
        with(model) {
            if (modelGenre?.type == ModelGenre.TYPE_GENRE_DISABLED) {
                return ModelGenre.TYPE_GENRE_DISABLED
            }
            if (modelGenre?.type == ModelGenre.TYPE_GENRE_ENABLED) {
                return ModelGenre.TYPE_GENRE_ENABLED
            }
            if (modelFilm?.type == ModelFilm.TYPE_FILM) {
                return ModelFilm.TYPE_FILM
            }
            if (modelLabel?.type == ModelLabel.TYPE_LABEL) {
                return ModelLabel.TYPE_LABEL
            } else {
                throw RuntimeException("Unknown film type")
            }
        }
    }

    private fun sortListByGenre(position: Int, type: Int): ArrayList<ModelItemRV> {
        val list = getDataList()//получение основного объекта лист Data
        if (type == TYPE_GENRE_DISABLED) {//если сортировка вызвана от объекта типа TYPE_GENRE_DISABLED
            setDataListSort(list)//записываем в объект сортировки основной объект
            val listSort = getDataListSort()

            val selectedGenre =
                listSort[position].modelGenre?.nameGenre //имя выбранного жанра из Data
            val listSortByGenre = ArrayList<ModelItemRV>()
            val listNoFilms = ArrayList<ModelItemRV>()
            val listFilms = ArrayList<ModelItemRV>()
            for (model in listSort) {
                if (getType(model) != TYPE_FILM) {
                    listNoFilms.add(model)
                } else {
                    for (genre in model.modelFilm?.genres.orEmpty()) {//отсеивание фильмов по жанру
                        if (genre == selectedGenre) {
                            listFilms.add(model)
                        }
                    }
                }
            }
            listFilms.sortWith(compareBy { it.modelFilm?.localName })//сортировка по имени
            listSortByGenre.addAll(listNoFilms)
            listSortByGenre.addAll(listFilms)
            setDataListSort(listSortByGenre)//записываем в объект сортировки
            return listSortByGenre
        } else if (type == TYPE_GENRE_ENABLED) {
            setDataListSort(list)
            return getDataListSort()

        } else if (type == TYPE_FILM) {
            return getDataListSort()
        } else {
            //записываем в объект сортировки
            return getDataListSort()
        }
    }

    private suspend fun setDataList(list: ArrayList<ModelItemRV>?) {
        list?.let {
            model.listModelItemRV = list

        }
    }

    private fun setDataListSort(list: ArrayList<ModelItemRV>?) {
        list?.let {
            model.listModelItemRVSort = list
        }
    }

    private fun getDataList(): ArrayList<ModelItemRV> {
        val list: ArrayList<ModelItemRV>?
        list = model.listModelItemRV
        return list
    }

    private fun getDataListSort(): ArrayList<ModelItemRV> {
        val list: ArrayList<ModelItemRV>?
        list = model.listModelItemRVSort
        return list
    }

    private fun setViewAdapterRV(list: ArrayList<ModelItemRV>) {
        view.listAdapter(list)
    }

    private fun getViewAdapterRV(): FilmsAdapter {
        return view.getAdapterRV()
    }


    fun destroy() {
        scope.cancel()
        scopeMain.cancel()
    }

    companion object {
        const val TYPE_GENRE_ENABLED = 100
        const val TYPE_GENRE_DISABLED = 101
        const val TYPE_FILM = 200
        const val TYPE_LABEL = 300
        const val TYPE_FRAGMENT_LIST_FILMS = 400
        const val TYPE_FRAGMENT_MOVIE_INFORMATION = 401

    }
}