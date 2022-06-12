package com.example.moviesearch.mvp.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.OpenFragment
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.mvp.presenter.PresenterActivity

class MainActivity : AppCompatActivity(), OpenFragment {
    private lateinit var binding: ActivityMainBinding
    private val defaultTitle = "Главная"
    private var paramPosition: Int? = null
    private var paramType: Int = TYPE_FRAGMENT_LIST_FILMS
    private var title: String = defaultTitle
    private lateinit var presenterActivity: PresenterActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenterActivity = PresenterActivity(this)

        presenterActivity.readSavedInstanceState(savedInstanceState)//чтение savedInstanceState
        openFragment(paramPosition, paramType, title)
    }

    fun getDefaultTitle(): String {
        return defaultTitle
    }

    fun setParamType(param: Int) {
        paramType = param
    }

    fun setTitle(titleBar: String) {
        title = titleBar
    }

    override fun openFragment( //выбор фрагмента
        position: Int?,
        fragmentType: Int,
        titleActionBar: String
    ) {
        presenterActivity.openFragment(
            position,
            fragmentType,
            titleActionBar,
            supportFragmentManager,
            supportActionBar
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_TYPE", paramType)
        Log.i("Logs",paramType.toString())
        outState.putString("KEY_TITLE", title)
        Log.i("Logs",title)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        title=defaultTitle
        paramType=TYPE_FRAGMENT_LIST_FILMS
        presenterActivity.backPress(supportActionBar, defaultTitle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenterActivity.optionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TYPE_FRAGMENT_LIST_FILMS = 400
        const val TYPE_FRAGMENT_MOVIE_INFORMATION = 401
    }
}