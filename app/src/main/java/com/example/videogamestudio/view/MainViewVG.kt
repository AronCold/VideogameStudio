package com.example.videogamestudio.view

import proto.Game

interface MainViewVG {

    var recyclerGames : List<Game>
    var historialJuegos : List<Game>
    fun showError(message: String)
    fun updateGames(games : List<Game>)
    fun updateRecycler()
}