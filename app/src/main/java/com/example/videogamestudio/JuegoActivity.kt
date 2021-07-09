package com.example.videogamestudio

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.videogamestudio.model.Videogame
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import proto.Game
import kotlin.time.measureTimedValue

class JuegoActivity : AppCompatActivity() {

    var historialJuegos = emptyList<Videogame>()

    lateinit var videogame : Videogame

    lateinit var tvName : TextView
    lateinit var tvGenre : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        tvName = findViewById(R.id.tvName)
        //tvGenre = findViewById(R.id.)

        //"recibir historial de juegos para meter en sharedPreferences"
        videogame = intent.getSerializableExtra("Juego") as Videogame
        tvName.setText(videogame.name)
        //tvGenre.setText(videogame.genres)

        //val name:String, val releaseDate:Date,
        //    val genres: List<Genre>, val involved_companies: MutableList<InvolvedCompany>,
        //    val platforms: MutableList<Platform>, val image:String,
        //    val aggregated_rating: Double, val summary: String) : Serializable

        //"Recibir juego en el intent")
        //"Mostrar datos y poner botón")
        //"Llamar a la siguiente actividad de busqueda relacionada")

        actualizarHistorial(videogame)
    }

    private fun actualizarHistorial(videogame: Videogame) {
        //recibir historial
        var mPrefs = getSharedPreferences("historialJuegos", MODE_PRIVATE)
        var historialLista: MutableList<Videogame> = ArrayList()
        var historialJSONString: String = ""

        if(!mPrefs.getString("listaHistorial", "").equals("")){
            historialJSONString = mPrefs.getString("listaHistorial", "")!!
            val type = object : TypeToken<List<Videogame>>() {}.type
            historialLista = Gson().fromJson(historialJSONString, type)
        }

        //modificar lista
        historialLista.add(videogame)

        Log.d("Juego añadido", "Se ha añadido un juego en la lista " + historialLista.size)
        //guardarLista
        var editor : SharedPreferences.Editor = mPrefs.edit()
        historialJSONString = Gson().toJson(historialLista);
        editor.putString("listaHistorial", historialJSONString);
        editor.commit();
        Log.d("Juego añadido", "Lista guardada")
    }
}