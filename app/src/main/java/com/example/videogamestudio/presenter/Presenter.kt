package com.example.videogamestudio.presenter

import android.util.Log
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.view.MainViewVG
import kotlinx.coroutines.*
import proto.Game

class Presenter(val view: MainViewVG, val model: Model) {
    var games = emptyList<Game>()

    init {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                model.setToken()
            }
            GlobalScope.launch { Log.d("PideJuegos", "aaa") }
        }

    }

    @DelicateCoroutinesApi
    fun getGamesByName(query: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Log.d("PideJuegos", "Estoy en la corrutina que bloquea, pido juegos")
                model.askGamesByName(query)
            }
            Log.d("PideJuegos", "Actualizando juegos del view")
            games = model.getGames()
            view.updateGames(games)
            view.updateRecycler()
        }
    }
}