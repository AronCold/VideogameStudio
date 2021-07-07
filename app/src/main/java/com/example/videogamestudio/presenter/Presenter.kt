package com.example.videogamestudio.presenter

import android.util.Log
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.view.MainViewVG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game

class Presenter(val view: MainViewVG, val model: Model) {
    var games = emptyList<Game>()

    init {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                model.setToken()
            }
        }
    }

    fun getGames(query: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                games = model.getGames(query)
            }
        }
    }
}