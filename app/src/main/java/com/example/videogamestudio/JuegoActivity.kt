package com.example.videogamestudio

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
    lateinit var tvReleaseDate : TextView
    lateinit var tvGenre : TextView
    lateinit var tvPublisher : TextView
    lateinit var tvConsoles : TextView
    lateinit var tvRating : TextView
    lateinit var tvInfo : TextView

    lateinit var ivImage : ImageView

    lateinit var btnRelated : Button
    lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        tvName = findViewById(R.id.tvName)
        tvGenre = findViewById(R.id.tvGenre)
        tvReleaseDate = findViewById(R.id.tvReleaseDate)
        tvPublisher = findViewById(R.id.tvPublisher)
        tvConsoles = findViewById(R.id.tvConsoles)
        tvRating = findViewById(R.id.tvRating)
        tvInfo = findViewById(R.id.tvInfo)

        ivImage = findViewById(R.id.ivVideogame)

        btnRelated = findViewById(R.id.rsButton)

        //recibe juego
        videogame = intent.getSerializableExtra("Juego") as Videogame
        tvName.text = videogame.name
        tvReleaseDate.text = videogame.releaseDate.toString()
        tvGenre.text = videogame.genres.toString()
        tvPublisher.text = videogame.involved_companies.toString()
        tvConsoles.text = videogame.platforms.toString()


        //"Recibir juego en el intent")
        //"Mostrar datos y poner botón")
        //"Llamar a la siguiente actividad de busqueda relacionada")

        //actualizar historial de juegos para meter en sharedPreferences
        actualizarHistorial(videogame)

        context = this.applicationContext

        btnRelated.setOnClickListener {
            val datosApp = Intent(context, RelatedSearchActivity::class.java).apply {
                putExtra("Juego", videogame)
            }
            startActivity(datosApp)
        }
    }

    private fun actualizarHistorial(videogame: Videogame) {
        //recibir historial
        val mPrefs = getSharedPreferences("historialJuegos", MODE_PRIVATE)
        var historialLista: MutableList<Videogame> = ArrayList()
        var historialJSONString: String = ""

        if(!mPrefs.getString("listaHistorial", "").equals("")){
            historialJSONString = mPrefs.getString("listaHistorial", "")!!
            val type = object : TypeToken<List<Videogame>>() {}.type
            historialLista = Gson().fromJson(historialJSONString, type)
        }

        //comprobamos si el juego está ya en la lista
        if(historialLista.find{ it.name == videogame.name }  == null) {
            //si no está en la lista comprobamos si el limite alcanzado
            if (historialLista.size >= 30) {
                //si limite no alcanzado modificar lista
                historialLista.removeAt(historialLista.size - 1)
            }
            historialLista.add(0, videogame)
            Log.d("Juego añadido", "Se ha añadido un juego en la lista " + historialLista.size)
        }

        //guardarLista
        var editor : SharedPreferences.Editor = mPrefs.edit()
        historialJSONString = Gson().toJson(historialLista);
        editor.putString("listaHistorial", historialJSONString);
        editor.commit();
        Log.d("Juego añadido", "Lista guardada")
    }
}