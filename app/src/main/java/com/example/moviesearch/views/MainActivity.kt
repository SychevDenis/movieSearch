package com.example.moviesearch.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.weatherappcompose.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerViewFilms:RecyclerView
        val repository=Repository()
        CoroutineScope(Dispatchers.IO).launch {
            val result= repository.getListFilms()
            result?.let {
                Log.i("Log",it.body().toString())
                Log.i("Log",it.raw().toString())
            }
        }
    }
}