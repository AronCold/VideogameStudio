package com.example.videogamestudio.network

import android.content.Context
import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.TwitchAuthenticator
import com.api.igdb.request.games
import com.api.igdb.request.jsonGames
import com.example.videogamestudio.view.Videogame
import org.json.JSONArray
import org.json.JSONObject
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

    fun getGames(): ArrayList<Videogame> {
        return requestGames()
    }

    private fun requestGames(): ArrayList<Videogame> {
        var games = emptyList<Game>()
        val apicalypse = APICalypse()
            .fields("name")
            .sort("id", Sort.ASCENDING)
            .limit(10)
        try {
            games = IGDBWrapper.games(apicalypse)
            Log.d("PideJuegos", games.toString())
        } catch (e: RequestException) {
            Log.d("PideJuegos", "Eroor")
        }
        val videoGames = ArrayList<Videogame>()

        //val gamesObject = JSONObject(games)
        Log.d("PideJuegos", games[0].name)

        //for (juego in games) {
            //Log.d("PideJuegos", juego.toString())
            /*val countryObject: JSONObject = element as JSONObject
            val name = countryObject.getString(NAME_LABEL)
            val id = countryObject.getString(ID)
            countries.add(Country(id, name))
            return games*/
        //}
        return videoGames
    }
}



/*fun getToken(listener: Response.Listener<String>, errorListener: Response.ErrorListener){
/*val jsonObjectRequest = JsonObjectRequest(
    Request.Method.POST,
    "https://id.twitch.tv/oauth2/token?client_id=ct0gepnwg8kekcdqu41c3z5nj0agrh&client_secret=lw2u26m3ee2tdrpg9ud92ekylzxvmj&grant_type=client_credentials",
    null,
    { response -> processToken(response, listener)},
    { error -> errorListener.onErrorResponse(error)})
queue.add(jsonObjectRequest)*/
}

private fun processToken(response: JSONObject, listener: Response.Listener<String>) {

Log.d("Pide Toke", "Procesando token...")

var miToken = ""

try {
    miToken = response.getString("access_token")
} catch (e: JSONException) {
    Log.d("PideToken", "Error al pedir token")
    listener.onResponse(null)
}

Log.d("PideToken", miToken)
listener.onResponse(miToken)
}


fun getTestQuerry(listener: Response.Listener<List<Videogame>>, errorListener: Response.ErrorListener, token:String){

var jsonObjectRequest = object : JsonObjectRequest(
    Method.POST, BASE_URL + "/games", null,
    Response.Listener { response -> processQuerry(response, listener)},
    Response.ErrorListener{ error -> Log.d("PideGames", "eroor")
        errorListener.onErrorResponse(error) })
    {
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Client-ID"] = "ct0gepnwg8kekcdqu41c3z5nj0agrh"
            headers["Authorization"] = "Bearer $token"
            return headers
        }

        override fun getBody(): ByteArray {
            return "fields *".toByteArray()
        }
    }
// Add the request to the RequestQueue.
queue.add(jsonObjectRequest)

}

private fun processQuerry(response: JSONObject, listener: Response.Listener<List<Videogame>>) {
Log.d("PideGames","$response")
Log.d("PideGames", "Procesando JSON...")
val games=ArrayList<Videogame>()
/*try {
    val countryArray=response.getJSONArray(COUNTRIES)
    for(i in 0 until countryArray.length()){
        val countryObject: JSONObject =countryArray[i]as JSONObject
        val name=countryObject.getString(NAME_LABEL)
        val id=countryObject.getString(ID)
        countries.add(Country(id, name))
    }
} catch (e: JSONException){
    listener.onResponse(null)
}
countries.sortBy {it.name}
Log.d("COVIDStats", "Primer país: ${countries[0]}")*/
listener.onResponse(games)
}


fun getCountries(listener: Response.Listener<List<Country>>, errorListener: Response.ErrorListener){
val url="$BASE_URL/$COUNTRIES"
Log.d("COVIDStats", "El URL es $url")
val jsonObjectRequest= JsonObjectRequest(
    Request.Method.GET, url, null,
    { response -> processCountries(response, listener)},
    { error -> errorListener.onErrorResponse(error) })
queue.add(jsonObjectRequest)
}

private fun processCountries(response: JSONObject, listener: Response.Listener<List<Country>>) {
Log.d("COVIDStats", "Procesando JSON...")
val countries=ArrayList<Country>()
try {
    val countryArray=response.getJSONArray(COUNTRIES)
    for(i in 0 until countryArray.length()){
        val countryObject: JSONObject =countryArray[i]as JSONObject
        val name=countryObject.getString(NAME_LABEL)
        val id=countryObject.getString(ID)
        countries.add(Country(id, name))
    }
} catch (e: JSONException){
    listener.onResponse(null)
}
countries.sortBy {it.name}
Log.d("COVIDStats", "Primer país: ${countries[0]}")
listener.onResponse(countries)
}*/