package com.example.videogamestudio.view

import com.example.videogamestudio.model.Videogame
import proto.Game

interface MainViewVG {

    var recyclerGames : List<Videogame>
    var historialJuegos : List<Videogame>
    fun showError(message: String)
    fun updateGames(games : List<Game>)
    fun updateRecycler()
}