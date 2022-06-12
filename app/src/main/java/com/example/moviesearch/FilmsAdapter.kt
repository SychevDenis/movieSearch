package com.example.moviesearch

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.FilmIconBinding
import com.example.moviesearch.databinding.GenreDisabledBinding
import com.example.moviesearch.databinding.GenreEnabledBinding
import com.example.moviesearch.databinding.LabelBinding
import com.example.moviesearch.modelAdapterRV.ModelFilm
import com.example.moviesearch.modelAdapterRV.ModelGenre
import com.example.moviesearch.modelAdapterRV.ModelItemRV
import com.example.moviesearch.modelAdapterRV.ModelLabel
import com.squareup.picasso.Picasso


class FilmsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onModelItemRVClickListener: ((ModelItemRV, Int) -> Unit)? = null

    var listModelItemRV = ArrayList<ModelItemRV>()
        //при изменении списка, перерерисоваем RV
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.i("Log", listModelItemRV.toString())
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_GENRE_DISABLED -> ModelGenreDisabledViewHolder(
                inflater.inflate(
                    R.layout.genre_disabled,
                    parent,
                    false
                )
            )
            TYPE_GENRE_ENABLED -> ModelGenreEnabledViewHolder(
                inflater.inflate(
                    R.layout.genre_enabled,
                    parent,
                    false
                )
            )
            TYPE_FILM -> ModelFilmViewHolder(
                inflater.inflate(
                    R.layout.film_icon,
                    parent,
                    false
                )
            )
            TYPE_LABEL -> ModelLabelViewHolder(
                inflater.inflate(
                    R.layout.label,
                    parent,
                    false
                )
            )
            else -> throw RuntimeException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener {
            onModelItemRVClickListener?.invoke(listModelItemRV[position], position)
        }
        //заполнение RV холдерами в 1 или 2 колонки
        if (typeGenre(listModelItemRV[position]) || typeLabel(listModelItemRV[position]))
        //если заполняем жанры или лэйблы, то
        {
            useOneColumn(holder)//использовать одну колонку
        }
        val listModelItemRV = listModelItemRV

        when (holder) {
            is ModelGenreDisabledViewHolder -> {
                listModelItemRV[position].modelGenre?.let { holder.bind(it) }
            }
            is ModelGenreEnabledViewHolder -> {
                listModelItemRV[position].modelGenre?.let { holder.bind(it) }
            }
            is ModelFilmViewHolder -> {
                listModelItemRV[position].modelFilm?.let { holder.bind(it) }
            }
            is ModelLabelViewHolder ->
                listModelItemRV[position].modelLabel?.let { holder.bind(it) }
        }
    }

    override fun getItemCount(): Int {
        return listModelItemRV.size

    }

    override fun getItemViewType(position: Int): Int {//определение типа
        with(listModelItemRV[position]) {
            when {
                typeGenre(this) -> { //Жанр
                    return when (modelGenre?.type) {
                        TYPE_GENRE_ENABLED -> {
                            //  Log.i("Log", "TYPE_GENRE_ENABLED $position")
                            TYPE_GENRE_ENABLED
                        }
                        TYPE_GENRE_DISABLED -> {
                            //    Log.i("Log", "TYPE_GENRE_DISABLED $position")
                            TYPE_GENRE_DISABLED
                        }
                        else -> {
                            throw RuntimeException("Unknown genre type")
                        }
                    }
                }
                typeFilm(this) -> {//Фильм
                    when (modelFilm?.type) {
                        TYPE_FILM -> {
                            //    Log.i("Log", "TYPE_FILM $position")
                            return TYPE_FILM
                        }
                        else -> {
                            throw RuntimeException("Unknown film type")
                        }
                    }
                }
                typeLabel(this) -> {  //Лэйбл
                    when (modelLabel?.type) {
                        TYPE_LABEL -> {
                            // Log.i("Log", "TYPE_LABEL $position")
                            return TYPE_LABEL
                        }
                        else -> {
                            throw RuntimeException("Unknown film type")
                        }
                    }
                }
                else -> {
                    throw RuntimeException("Unknown film type")
                }
            }
        }
    }

    class ModelGenreDisabledViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) { //VH Genre disabled
        private val genreBinding = GenreDisabledBinding.bind(view)
        fun bind(modelGenre: ModelGenre) {
            genreBinding.TVGenre.text = modelGenre.nameGenre
        }
    }

    class ModelGenreEnabledViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {  //VH Genre enabled
        private val genreBinding = GenreEnabledBinding.bind(view)
        fun bind(modelGenre: ModelGenre) {
            genreBinding.TVGenre.text = modelGenre.nameGenre

        }
    }
    class ModelFilmViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {  //VH Film
        private val filmIconBinding = FilmIconBinding.bind(view)
        fun bind(modelFilm: ModelFilm) {
            Picasso.get().load(modelFilm.movieCoverImageURL).placeholder(R.drawable.image_not_found)
                .centerCrop().fit().into(filmIconBinding.IVFilmIcon)//загрузка фото из сети
            filmIconBinding.TVFilmIcon.text = modelFilm.localName
        }
    }

    class ModelLabelViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {  //VH Film
        private val labelBinding = LabelBinding.bind(view)
        fun bind(modelLabel: ModelLabel) {
            labelBinding.TVLabel.text = modelLabel.text
        }
    }

    private fun useOneColumn(holder: RecyclerView.ViewHolder) {//использовтаь одну колонку
        val params: GridLayoutManager.LayoutParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        holder.itemView.layoutParams = params
    }


    private fun typeGenre(listModelItemRV: ModelItemRV): Boolean {//проверка типа
        return listModelItemRV.modelGenre != null && listModelItemRV.modelFilm == null && listModelItemRV.modelLabel == null
    }

    private fun typeFilm(listModelItemRV: ModelItemRV): Boolean {
        return listModelItemRV.modelGenre == null && listModelItemRV.modelFilm != null && listModelItemRV.modelLabel == null
    }

    private fun typeLabel(listModelItemRV: ModelItemRV): Boolean {
        return listModelItemRV.modelGenre == null && listModelItemRV.modelFilm == null && listModelItemRV.modelLabel != null
    }

    companion object {
        const val TYPE_GENRE_ENABLED = 100
        const val TYPE_GENRE_DISABLED = 101
        const val TYPE_FILM = 200
        const val TYPE_LABEL = 300
    }
}