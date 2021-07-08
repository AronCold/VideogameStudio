package com.example.videogamestudio.network

import android.content.Context
import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.TwitchAuthenticator
import com.api.igdb.request.games
import kotlinx.coroutines.*
import proto.Game

const val BASE_URL = "https://api.igdb.com/v4"

const val CLIENT_ID = "ct0gepnwg8kekcdqu41c3z5nj0agrh"
const val CLIENT_SECRET = "bwoa1kzrhpvxtpi9rf79ntpg5g5hn3"
//https://id.twitch.tv/oauth2/token -> link

class Network private constructor(context: Context) {

    companion object : SingletonHolder<Network, Context>(::Network)

    var twitch = TwitchAuthenticator

    //val queue = Volley.newRequestQueue(context)

    var tokenString: String? = null

    var searchedGames = emptyList<Game>()


    fun setToken() {

        val token = twitch.requestTwitchToken(CLIENT_ID, CLIENT_SECRET)
        Log.d("PideJuegos", "Network: token es " + token!!.toString())

        TwitchAuthenticator.twitchToken
        tokenString = token.access_token

        IGDBWrapper.setCredentials(CLIENT_ID, tokenString!!)
        Log.d("PideJuegos", "Network: " + TwitchAuthenticator.twitchToken!!.access_token)
    }

    @DelicateCoroutinesApi
    suspend fun getGames(query: String?, busquedaRelacionada : Boolean) {
        if (busquedaRelacionada) {
            Log.d("BusquedaRelacionada", "Estoy en network")
        } else {
            var games = emptyList<Game>()
            val value = GlobalScope.async {
                val apicalypse = APICalypse()
                    .fields(
                        "*, " +
                                "involved_companies.company.name, " +
                                "genres.name, " +
                                "cover.url," +
                                "cover.height," +
                                "cover.width"
                    )
                    .search(query!!)
                    .limit(500)
                try {
                    games = IGDBWrapper.games(apicalypse)
                    //Log.d("PideJuegos", games.toString())
                    if (games.isNotEmpty()){
                        Log.d("PideJuegos", games[0].cover.height.toString())
                        Log.d("PideJuegos", games[0].cover.width.toString())
                        Log.d("PideJuegos", games[0].cover.url)
                    }
                    else Log.d("PideJuegos", "Juegos esta vacio")
                } catch (e: RequestException) {
                    Log.d("PideJuegos", "Eroor")
                }
            }
            println(value.await())
            searchedGames = games
        }
    }
}