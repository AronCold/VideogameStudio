package com.example.videogamestudio.model

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.example.videogamestudio.network.Network

class Model(context: Context) {

    private val network = Network.getInstance(context)

    fun getToken(listener: Response.Listener<String>, errorListener: Response.ErrorListener){
        Log.d("PideToken", "Estoy en el model." )
        network.getToken(listener, errorListener)
    }
}