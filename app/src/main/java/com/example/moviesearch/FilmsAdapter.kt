package com.example.moviesearch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.FilmIconBinding
import com.example.moviesearch.databinding.GenreDisabledBinding
import com.example.moviesearch.databinding.GenreEnabledBinding
import com.example.moviesearch.modelAdapterRV.ModelFilm
import com.example.moviesearch.modelAdapterRV.ModelGenre
import com.example.moviesearch.modelAdapterRV.ModelItemRV
import com.example.moviesearch.modelPojo.pojoModel.Films
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviesearch.databinding.LabelBinding
import com.example.moviesearch.modelAdapterRV.ModelLabel


class FilmsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun getData(it: Films) {//чтение и конвертация данных
        listModelItemRV = ConvertorPojoToAdapterRV(it).modelItemRV
    }

    private var listModelItemRV = ArrayList<ModelItemRV>()
        //при изменении списка, перерерисоваем RV
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

            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {//заполнение RV холдерами в 1 или 2 стобца
        if (listModelItemRV[position].modelGenre != null
            && listModelItemRV[position].modelFilm == null) {//если заполняем жанры, то
            val params: StaggeredGridLayoutManager.LayoutParams =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            params.isFullSpan = true //"растягиваем" делаем по одному элементу в списке
            holder.itemView.layoutParams = params
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
            is   ModelLabelViewHolder ->
                listModelItemRV[position].ModelLabel?.let { holder.bind(it) }
            }
        }

    override fun getItemCount(): Int {
        Log.i("Log", "size ListModelItemRV ${listModelItemRV.size}")
        return listModelItemRV.size

    }

    override fun getItemViewType(position: Int): Int {//определение типа
        with(listModelItemRV[position]) {
            if (modelGenre != null && modelFilm == null && ModelLabel == null) { //Жанр
                when (modelGenre?.type) {
                    TYPE_GENRE_ENABLED -> {
                        Log.i("Log", "TYPE_GENRE_ENABLED $position")
                        return TYPE_GENRE_ENABLED
                    }
                    TYPE_GENRE_DISABLED -> {
                        Log.i("Log", "TYPE_GENRE_DISABLED $position")
                        return TYPE_GENRE_DISABLED
                    }
                    else -> {
                        throw IllegalArgumentException("Unknown genre type")
                    }
                }
            } else if (modelGenre == null && modelFilm != null && ModelLabel == null) {//Фильм
                when (modelFilm?.type) {
                    TYPE_FILM -> {
                        Log.i("Log", "TYPE_FILM $position")
                        return TYPE_FILM
                    }
                    else -> {
                        throw IllegalArgumentException("Unknown film type")
                    }
                }
            } else if (modelGenre == null && modelFilm == null && ModelLabel != null) {  //Лэйбл
                when (ModelLabel?.type) {
                    TYPE_LABEL -> {
                        Log.i("Log", "TYPE_LABEL $position")
                        return TYPE_LABEL
                    }
                    else -> {
                        throw IllegalArgumentException("Unknown film type")
                    }
                }
            } else {
                throw IllegalArgumentException("Unknown film type")
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
                .centerCrop().fit().into(filmIconBinding.IVFilm)//загрузка фото из сети
            filmIconBinding.TVFilm.text = modelFilm.localName
        }
    }

    class ModelLabelViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {  //VH Film
        private val labelBinding = LabelBinding.bind(view)
        fun bind(modelLabel: ModelLabel) {
            labelBinding.TVLabel.text = modelLabel.text
        }
    }

    companion object {
        const val TYPE_GENRE_ENABLED = 100
        const val TYPE_GENRE_DISABLED = 101
        const val TYPE_FILM = 200
        const val TYPE_LABEL = 300
    }
}