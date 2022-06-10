package com.example.moviesearch

import com.example.moviesearch.modelAdapterRV.ModelFilm
import com.example.moviesearch.modelAdapterRV.ModelGenre
import com.example.moviesearch.modelAdapterRV.ModelItemRV
import com.example.moviesearch.modelAdapterRV.ModelLabel
import com.example.moviesearch.modelPojo.pojoModel.Films

class ConvertorPojoToAdapterRV(it: Films) {
    //преобразователь pojo данных в список для адаптера
    private val modelItemRV = assemblyObjectForAdapter(it)

    private fun allGenres(it: Films): ArrayList<ModelGenre> {//создать список моделей жанров
        var listGenre = ArrayList<String>()
        for (film in it.films) { //поиск всех жантов в полученном объекте
            for (genre in film.genres) {
                listGenre.add(genre)
            }
        }
        listGenre = ArrayList(LinkedHashSet<String>(listGenre))//удаляем повторяющиеся элементы
        val listObjectGenre = ArrayList<ModelGenre>()//создаем модели всех жанров

        for (genre in listGenre) {
            listObjectGenre.add(ModelGenre(genre, ModelGenre.TYPE_GENRE_DISABLED))
        }
        return listObjectGenre
    }

    private fun allFilms(it: Films): ArrayList<ModelFilm> {//создать список моделей фильм
        val listFilm = ArrayList<ModelFilm>()
        for (film in it.films) {
            listFilm.add(
                ModelFilm(
                    film.id,
                    film.imageUrl,
                    film.localizedName,
                    film.name,
                    film.year,
                    film.rating,
                    film.genres,
                    film.description,
                    ModelFilm.TYPE_FILM
                )
            )
        }
        return listFilm
    }

    private fun assemblyObjectForAdapter(it: Films): ArrayList<ModelItemRV> {//сборка объекта для адаптера RV
        val listGenre = allGenres(it)//получить список моделей жанров
        val listFilm = allFilms(it)//получить список моделей жанров
        val listModelItemRV = ArrayList<ModelItemRV>()

        listModelItemRV.add(
            ModelItemRV(
                ModelLabel("Жанры", ModelLabel.TYPE_LABEL), null, null
            )
        )//добавить лэйбл жанры

        for (modelGenre in listGenre) {
            listModelItemRV.add(ModelItemRV(null, modelGenre, null))
        }

        listModelItemRV.add(
            ModelItemRV(
                ModelLabel("Фильмы", ModelLabel.TYPE_LABEL),
                null, null
            )
        )//добавить лэйбл фильмы

        for (modelList in listFilm) {
            listModelItemRV.add(ModelItemRV(null, null, modelList))
        }
        return listModelItemRV//итоговый объект для отправки в адаптер
    }

    fun result(): ArrayList<ModelItemRV> {
        return modelItemRV
    }
}