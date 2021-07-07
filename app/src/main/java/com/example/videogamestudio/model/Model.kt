package com.example.videogamestudio.model

import android.content.Context
import android.util.Log
import com.example.videogamestudio.network.Network
import proto.Game

class Model(context: Context) {

    private val network = Network.getInstance(context)

    /*fun getToken(listener: Response.Listener<String>, errorListener: Response.ErrorListener){
        Log.d("PideToken", "Estoy en el model." )
        network.getToken(listener, errorListener)
    }

    fun getTestQuerry(listener: Response.Listener<List<Videogame>>, errorListener: Response.ErrorListener, token:String) {
        Log.d("PideGames", "Estoy en el model." )
        network.getTestQuerry(listener, errorListener, token)
    }*/

    fun setToken(){
        Log.d("PideJuegos", "Model: Setting token")
        network.setToken()
    }

    fun getGames(query: String?): List<Game> {
        Log.d("PideJuegos", "Estoy en el model." )
        return network.getGames(query)
    }
}