package com.example.videogamestudio.network

import android.content.Context
import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.TwitchAuthenticator
import com.api.igdb.request.games
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game

const val BASE_URL= "https://api.igdb.com/v4"

const val CLIENT_ID = "ct0gepnwg8kekcdqu41c3z5nj0agrh"
const val CLIENT_SECRET = "bwoa1kzrhpvxtpi9rf79ntpg5g5hn3"
//https://id.twitch.tv/oauth2/token -> link

class Network private constructor(context: Context){

    companion object: SingletonHolder<Network, Context>(::Network)
    var twitch = TwitchAuthenticator

    //val queue = Volley.newRequestQueue(context)

    var tokenString : String? = null


    fun setToken(){

        val token = twitch.requestTwitchToken(CLIENT_ID, CLIENT_SECRET)
        Log.d("PideJuegos", "Network: token es " + token!!.toString())

        TwitchAuthenticator.twitchToken
        tokenString = token.access_token

        IGDBWrapper.setCredentials(CLIENT_ID, tokenString!!)
        Log.d("PideJuegos", "Network: " + TwitchAuthenticator.twitchToken!!.access_token)
    }

    fun getGames(query: String?): List<Game> {
        var games = emptyList<Game>()

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val apicalypse = APICalypse()
                    .fields("*, involved_companies.company.name, genres.name, cover.url")
                    .search(query!!)
                try {
                    games = IGDBWrapper.games(apicalypse)
                    Log.d("PideJuegos", games.toString())
                    if(!games.isEmpty())
                        Log.d("PideJuegos", games[0].name)
                } catch (e: RequestException) {
                    Log.d("PideJuegos", "Eroor")
                }
            }
        }

        return games
    }
}