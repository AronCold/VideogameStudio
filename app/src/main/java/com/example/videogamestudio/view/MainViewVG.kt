package com.example.videogamestudio.view

import proto.Game

interface MainViewVG {

    var games : List<Game>
    fun showError(message: String)
    var searchedGames: List<Game>
}