package com.example.moviesearch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.FilmIconBinding
import com.example.moviesearch.databinding.GenreDisabledBinding
import com.example.moviesearch.modelAdapterRV.ModelFilm
import com.example.moviesearch.modelAdapterRV.ModelGenre
import com.example.moviesearch.modelAdapterRV.ModelItemRV
import com.example.moviesearch.modelPojo.pojoModel.Films

class FilmsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun getData(it: Films) {//чтение и конвертация данных
        listModelItemRV = ConvertorPojoToAdapterRV(it).modelItemRV
    }

    private var listModelItemRV = ModelItemRV()
        //при изменении списка, перерерисоваем RV
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.i("Log", listModelItemRV.toString())
        }


    //    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelGenreViewHolder {
//        val layout = when (viewType) {
//            TYPE_GENRE_DISABLED -> R.layout.genre_disabled
//            TYPE_GENRE_ENABLED -> R.layout.genre_enabled
//            else -> throw RuntimeException("Unknown view type: $viewType")
//        }
//        val view =
//            LayoutInflater.from(parent.context)
//                .inflate(layout, parent, false)
//        return ModelGenreViewHolder(view)
//    }
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
            TYPE_FILM -> ModelGenreEnabledViewHolder(
                inflater.inflate(
                    R.layout.film_icon,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }
//    override fun onBindViewHolder(holder: ModelGenreViewHolder, position: Int) {
//        holder.bind(listModelFilm[position])
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listModelItemRV = listModelItemRV
        when (holder) {
            is ModelGenreDisabledViewHolder -> {
                holder.bind(listModelItemRV.listModelGenre[position])
            }
            is ModelGenreEnabledViewHolder -> {
                holder.bind(listModelItemRV.listModelGenre[position])
            }
            is ModelFilmViewHolder -> {
                holder.bind(listModelItemRV.listModelFilm[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return with(listModelItemRV) {
            listModelGenre.size
        }
    }

    override fun getItemViewType(position: Int): Int {//определение типа
        val modelGenre = listModelItemRV.listModelGenre[position]
        val modelFilm = listModelItemRV.listModelFilm[position]
        if (listModelItemRV.listModelGenre.size > position) {
            when (modelGenre.type) {
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
        } else {
            when (modelFilm.type) {
                TYPE_FILM -> {
                    Log.i("Log", "TYPE_FILM $position")
                    return TYPE_FILM
                }
                else ->{
                    throw IllegalArgumentException("Unknown film type")
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
        private val genreBinding = GenreDisabledBinding.bind(view)
        fun bind(modelGenre: ModelGenre) {
            genreBinding.TVGenre.text = modelGenre.nameGenre
        }
    }

    class ModelFilmViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {  //VH Film
        private val filmIconBinding = FilmIconBinding.bind(view)
        fun bind(modelFilm: ModelFilm) {
            filmIconBinding.TVFilm.text = modelFilm.localName
        }
    }

    companion object {
        const val TYPE_GENRE_ENABLED = 100
        const val TYPE_GENRE_DISABLED = 101
        const val TYPE_FILM = 201
    }
}


//    var filmsList = listOf<Films>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
//        val layout = when (viewType) {
//            VIEW_TYPE_DISABLED -> R.layout.genre_disabled
//            VIEW_TYPE_ENABLED -> R.layout.genre_enabled
//            else -> throw RuntimeException("Unknown view type: $viewType")
//        }
//        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
//        return ShopItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
//        val shopItem = shopList[position]
//        viewHolder.view.setOnLongClickListener {
//            true
//        }
//        viewHolder.tvName.text = shopItem.name
//    }
//
//    override fun onViewRecycled(viewHolder: ShopItemViewHolder) {
//        super.onViewRecycled(viewHolder)
//        viewHolder.tvName.text = ""
//        viewHolder.tvName.setTextColor(
//            ContextCompat.getColor(
//                viewHolder.view.context,
//                android.R.color.white
//            )
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return shopList.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        val item = shopList[position]
//        return if (item.enabled) {
//            VIEW_TYPE_ENABLED
//        } else {
//            VIEW_TYPE_DISABLED
//        }
//    }
//
//    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        val tvName = view.findViewById<TextView>(R.id.textGenre)
//    }
//
//    companion object {
//
//        const val VIEW_TYPE_ENABLED = 100
//        const val VIEW_TYPE_DISABLED = 101
//
//        const val MAX_POOL_SIZE = 30
//    }
//}
