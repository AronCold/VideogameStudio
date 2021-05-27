package com.example.videogamestudio.network

import android.content.Context
import com.android.volley.toolbox.Volley


//private const val BASE_URL= "https://api.igdb.com/v4/games/"
//private const val ID="id"

class Network private constructor(context: Context){
    companion object: SingletonHolder<Network, Context>(::Network)

    //val queue = Volley.newRequestQueue(context)
}