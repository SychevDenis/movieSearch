package com.example.moviesearch.mvp.model

import android.util.Log
import com.example.moviesearch.modelAdapterRV.ModelItemRV

object Data {
    var listModelItemRV = ArrayList<ModelItemRV>()
        set(value) {
            field=value
            Log.i("Log", "Данные сохранены")
        }
    var listModelItemRVSort = ArrayList<ModelItemRV>()
}