package com.example.videogamestudio.presenter

import android.util.Log
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.view.MainViewVG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game

lateinit var tokenAcceso:String


class Presenter(val view: MainViewVG, val model: Model) {
    fun getGames(query: String?): List<Game> {
        return model.getGames(query)
    }

    private var games = emptyList<Game>()
        set(value) {
            field = value
            view.games = games
        }

    init {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                model.setToken()
            }
        }
    }
        /*model.getToken(
            {
                response ->
                Log.d("PideToken", "Estoy de vuelta en el onResponse, el token es: $response")
                tokenAcceso = response
                model.getTestQuerry({
                        response ->
                    Log.d("PideQuerry", "Estoy de vuelta en el onResponse, la lista de juegos es: $response")
                }, { error -> view.showError(error.toString())}, tokenAcceso)
        }, { error -> view.showError(error.toString())})*/
}