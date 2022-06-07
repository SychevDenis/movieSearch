package com.example.moviesearch.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesearch.R
import com.example.moviesearch.FilmsAdapter
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.weatherappcompose.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = FilmsAdapter()
    private val repository = Repository()
    private val scope = CoroutineScope(Dispatchers.IO)
    private val scopeMain = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getResponseApi()
    }

    @SuppressLint("WrongConstant")
    private fun init() {
        binding.apply {
            RWListFilms.layoutManager = GridLayoutManager(this@MainActivity, 1)
            RWListFilms.adapter = adapter
        }
    }

    private fun getResponseApi() {
        scope.launch {//выполнить запрос api в другом потоке
            val result = repository.getListFilms()?.body()
            result?.let {
                scopeMain.launch { //вывести в главном потоке
                    adapter.getData(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        scopeMain.cancel()
    }
}