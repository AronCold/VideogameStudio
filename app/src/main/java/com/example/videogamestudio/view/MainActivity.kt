package com.example.videogamestudio.view


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogamestudio.R
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.model.Videogame
import com.example.videogamestudio.presenter.Presenter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import proto.Game
import java.lang.reflect.Type


class MainActivity : AppCompatActivity(), MainViewVG {

    override var recyclerGames = emptyList<Videogame>()
    override var historialJuegos = emptyList<Videogame>()
    override var JUEGOSVACIO = emptyList<Videogame>()

    lateinit var presenter: Presenter

    lateinit var rvVideogames: RecyclerView

    lateinit var searchButton: SearchView

    lateinit var adapter: VideogameAdapter

    lateinit var contexto: Context

    lateinit var properties: ArrayList<String>


    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = Model(applicationContext)

        presenter = Presenter(this, model)

        rvVideogames = findViewById(R.id.rvVideogames)

        searchButton = findViewById(R.id.search_button)

        contexto = applicationContext



        searchButton.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("PideJuegos", "searchbutton pulsado")
                if (!query.equals("sHistory")) {
                    Log.d("PideJuegos", "llamando a presenter.getGames")
                    presenter.getGamesByName(query)
                } else {
                    //si la cadena es nula actualizar el recicler con el historial de juegos
                    historialJuegos = recibeHistorial()
                    Log.d("Historial", "Recibimos el historial")
                    updateRecycler(historialJuegos)
                }
                searchButton.clearFocus()
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        //"Recibir historial"
        historialJuegos = recibeHistorial()
        Log.d("Historial", "Recibimos el historial")

        //recibimos propiedades
        properties = arrayListOf("Empty", "Empty", "Empty")
        if (!intent.getStringArrayListExtra("Properties").isNullOrEmpty()) {
            properties = intent.getStringArrayListExtra("Properties") as ArrayList<String>
            Log.d("RelatedSearch", "El intent no está vacío")
        }

        if (properties[0] == "Empty"
            && properties[1] == "Empty"
            && properties[2] == "Empty"
        ) {
            Log.d("RelatedSearch", "Propiedades está vacío, iniciando historial")
            initRecycler(historialJuegos)
        } else {
            Log.d("RelatedSearch", "Propiedades no está vacío, requesting game")
            initRecycler(JUEGOSVACIO)
            presenter.getGamesByProperties(properties)
        }
    }

    private fun recibeHistorial(): MutableList<Videogame> {
        val mPrefs = getSharedPreferences("historialJuegos", MODE_PRIVATE)

        if (!mPrefs.getString("listaHistorial", "").equals("")) {
            val historialJSONString = mPrefs.getString("listaHistorial", "")
            val type = object : TypeToken<MutableList<Videogame?>?>() {}.type
            return Gson().fromJson(historialJSONString, type)
        }
        return ArrayList()
    }


    private fun initRecycler(juegos: List<Videogame>) {
        rvVideogames.layoutManager = LinearLayoutManager(this)
        adapter = VideogameAdapter(juegos)
        rvVideogames.adapter = adapter
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun updateGames(games: List<Game>) {

        var listaTemporal: MutableList<Videogame> = ArrayList()
        if (!games.isEmpty()) {
            for (i in 0..games.size - 1) {
                listaTemporal.add(convertirJuego(games[i]))
            }
        }

        recyclerGames = listaTemporal
    }

    private fun convertirJuego(game: Game): Videogame {
        var keywords: MutableList<String> = ArrayList()
        game.keywordsList.forEach { keywords.add(it.name) }
        Log.d("PideJuegos", game.name + " : " + keywords.toString())
        return Videogame(
            game.name,
            java.util.Date(game.firstReleaseDate.seconds * 1000),
            game.genresList,
            game.involvedCompaniesList,
            game.platformsList,
            "https:" + game.cover.url.replace("t_thumb", "t_cover_big"),
            game.aggregatedRating,
            game.summary,
            game.id.toInt(),
            keywords
        )
    }

    override fun updateRecycler(juegos: List<Videogame>) {
        Log.d("PideJuegos", "Updating Recycler")
        adapter.videogames = juegos
        adapter.notifyDataSetChanged();
    }

    override fun onDestroy() {
        super.onDestroy()
        //TOD("Guardar historial")
        var mPrefs = getSharedPreferences("historialJuegos", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = mPrefs.edit()

        var historialJSONString = Gson().toJson(historialJuegos);
        editor.putString("listaHistorial", historialJSONString);
        editor.commit();
    }
}
