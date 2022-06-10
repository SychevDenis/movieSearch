package com.example.moviesearch

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list_film)
        if (savedInstanceState==null) {
            Log.i("Init","Init")
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.parseColor("#323232")))
            it.title = "Главная"
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentListFilms.newInstance())
            .commit()
    }
}