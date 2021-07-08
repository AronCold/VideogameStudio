package com.example.videogamestudio.model

import android.content.Context
import android.util.Log
import com.example.videogamestudio.network.Network
import kotlinx.coroutines.*
import proto.Game

class Model(context: Context) {

    private val network = Network.getInstance(context)

    fun setToken() {
        Log.d("PideJuegos", "Model: Setting token")
        network.setToken()
    }

    @DelicateCoroutinesApi
    suspend fun askGamesByName(query: String?) {
        network.getGames(query, false)
    }

    fun getGames(): List<Game>{
        return network.searchedGames
    }
}