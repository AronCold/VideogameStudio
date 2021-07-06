package com.example.videogamestudio.view

import proto.Game

interface MainViewVG {

    var games : ArrayList<Videogame>
    fun showError(message: String)
}