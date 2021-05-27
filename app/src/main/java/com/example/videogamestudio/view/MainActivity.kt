package com.example.videogamestudio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.videogamestudio.R
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var buscar:Button

    var token=""

    val queue = Volley.newRequestQueue(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscar=findViewById(R.id.buscar)

        // Obtenemos el token
        getToken({ token ->
            if (token!=null){
                this.token = token

            } else{
                Log.d("token", "No token")
            }
        }, { error -> Log.d("token","No token")})

        buscar.setOnClickListener {
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "https://id.twitch.tv/oauth2/token"+
                    "?client_id=ct0gepnwg8kekcdqu41c3z5nj0agrh"+
                    "&client_secret=0kmngavgupurbrvc1nydekhug7jnok"+
                    "&grant_type=client_credentials"

            // Request a string response from the provided URL.
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, null,
                Response.Listener<JSONObject>
                { response ->
                    // Display the first 500 characters of the response string.
                    buscar.text = "Response is: Todo ha ido bien"
                },
                { error -> buscar.text = "That didn't work!" })

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)
        }

    }

    fun getToken(listener: Response.Listener<String>, errorListener: Response.ErrorListener) {
        val url = "https://id.twitch.tv/oauth2/token"+
                "?client_id=ct0gepnwg8kekcdqu41c3z5nj0agrh"+
                "&client_secret=0kmngavgupurbrvc1nydekhug7jnok"+
                "&grant_type=client_credentials"
        val jsonObjectRequest=JsonObjectRequest(Request.Method.GET, url, null,
            { response -> var token = ""
                val tokenArray = response.getJSONArray("access_token")
                token = tokenArray[0] as String
                Log.d("token", "Token: ${token}")
            },
            { error -> buscar.text = "That didn't work!" })
        queue.add(jsonObjectRequest)
    }
}