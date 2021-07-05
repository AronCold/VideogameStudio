package com.example.videogamestudio.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

private const val BASE_URL= "https://api.igdb.com/v4"

//ct0gepnwg8kekcdqu41c3z5nj0agrh -> id cliente
//lw2u26m3ee2tdrpg9ud92ekylzxvmj -> secreto
//https://id.twitch.tv/oauth2/token -> link

class Network private constructor(context: Context){

    companion object: SingletonHolder<Network, Context>(::Network)

    val queue = Volley.newRequestQueue(context)

    fun getToken(listener: Response.Listener<String>, errorListener: Response.ErrorListener){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "https://id.twitch.tv/oauth2/token?client_id=ct0gepnwg8kekcdqu41c3z5nj0agrh&client_secret=lw2u26m3ee2tdrpg9ud92ekylzxvmj&grant_type=client_credentials",
            null,
            { response -> processToken(response, listener)},
            { error -> errorListener.onErrorResponse(error)})
        queue.add(jsonObjectRequest)
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


    fun textQuerry(listener: Response.Listener<String>, errorListener: Response.ErrorListener, token:String){

        //SIGUIENTE JSON
        val params = HashMap<String,String>()
        params["Client-ID"] = "ct0gepnwg8kekcdqu41c3z5nj0agrh"
        params["Authorization"] = "Bearer $token"
        val jsonObject = JSONObject(params as Map<*, *>)


        var jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, BASE_URL +"/games", jsonObject,
            { response ->
                // Display the first 100 characters of the response string.
                Log.d("getGames","$response")
                listener.onResponse(response.toString())
            },
            { error ->
                errorListener.onErrorResponse(error)
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    /*
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
}