package com.example.videogamestudio.presenter

import android.util.Log
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.view.MainViewVG
import com.example.videogamestudio.view.Videogame

lateinit var tokenAcceso:String

class Presenter(val view: MainViewVG, val model: Model) {
    var videogame: Videogame? = null

    init {
        model.getToken(
            {
                response ->
                Log.d("PideToken", "Estoy de vuelta en el onResponse, el token es: $response")
                tokenAcceso = response
        }, { error -> view.showError(error.toString())})
    }
}