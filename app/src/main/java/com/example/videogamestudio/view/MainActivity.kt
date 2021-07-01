package com.example.videogamestudio.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.videogamestudio.R
import com.example.videogamestudio.network.SingletonHolder
import org.json.JSONObject
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    val videogames = listOf(
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"),
        Videogame("Spiderman", "Marvel", "Peter Parker", " Wii", "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg")



    )

    lateinit var rvVideogames : RecyclerView

    // Instantiate the RequestQueue.

    val url = "https://api.igdb.com/v4"
    // Request a JSON object response from the provided URL.

    lateinit var token:String= null

    //ct0gepnwg8kekcdqu41c3z5nj0agrh -> id cliente
    //lw2u26m3ee2tdrpg9ud92ekylzxvmj -> secreto
    //https://id.twitch.tv/oauth2/token -> link


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)

        var tokenJsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "https://id.twitch.tv/oauth2/token?" +
                    "client_id=ct0gepnwg8kekcdqu41c3z5nj0agrh" +
                    "&client_secret=lw2u26m3ee2tdrpg9ud92ekylzxvmj" +
                    "&grant_type=client_credentials", null,
            { response ->
                // Display the first 100 characters of the response string.
                Log.d("getToken","$response")
                token = response.getString("access_token")
                Log.d("getToken", token)


            },
            { error ->
                error.printStackTrace()
            })

        // Add the request to the RequestQueue.
        queue.add(tokenJsonObjectRequest)


        //SIGUIENTE JSON
        val params = HashMap<String,String>()
        params["Client-id"] = "ct0gepnwg8kekcdqu41c3z5nj0agrh"
        params["Authorization"] = "Bearer $token"
        val jsonObject = JSONObject(params as Map<*, *>)


        var jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url+"/games", jsonObject,
            { response ->
                // Display the first 100 characters of the response string.
                Log.d("getGames","$response")
            },
            { error ->
                error.printStackTrace()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

        rvVideogames= findViewById(R.id.rvVideogames)

        initRecycler()
    }

    private fun initRecycler(){
        rvVideogames.layoutManager =LinearLayoutManager(this)
        val adapter = VideogameAdapter(videogames)
        rvVideogames.adapter=adapter
    }
}