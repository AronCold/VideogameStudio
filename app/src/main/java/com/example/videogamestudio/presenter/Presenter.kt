package com.example.videogamestudio.presenter

import android.util.Log
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.view.MainViewVG
import com.example.videogamestudio.view.Videogame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game

lateinit var tokenAcceso:String


class Presenter(val view: MainViewVG, val model: Model) {
    private var games = ArrayList<Videogame>()
        set(value) {
            field = value
            view.games = games
        }

    init {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                model.setToken()
            }
            GlobalScope.launch {
                games = model.getGames()!!
                var game = games[0]
                Log.d("test", "hola")
                //var videogame = Videogame(games[0].name, 10, "a", "a", "a")
                //Log.d("PideJuegos", game!!.toString())
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