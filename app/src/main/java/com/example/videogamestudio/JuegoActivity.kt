package com.example.videogamestudio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import proto.Game

class JuegoActivity : AppCompatActivity() {

    var historialJuegos = emptyList<Game>()
    lateinit var textview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        textview = findViewById(R.id.test)

        TODO("recibir historial de juegos para meter en sharedPreferences")
        TODO("Recibir juego en el intent")
        TODO("Mostrar datos y poner botón")
        TODO("Llamar a la siguiente actividad de busqueda relacionada")
    }
}