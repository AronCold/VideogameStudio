package com.example.videogamestudio.view


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogamestudio.R
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.presenter.Presenter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import proto.Game


class MainActivity : AppCompatActivity(), MainViewVG {

    override var recyclerGames = emptyList<Game>()
    override var historialJuegos = emptyList<Game>()

    lateinit var presenter: Presenter

    lateinit var rvVideogames: RecyclerView

    lateinit var searchButton: SearchView

    lateinit var adapter: VideogameAdapter

    lateinit var contexto: Context


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
                if (query != null) {
                    Log.d("PideJuegos", "llamando a presenter.getGames")
                    presenter.getGamesByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        initRecycler()

        TODO("Recibir historial")
    }


    private fun initRecycler() {
        rvVideogames.layoutManager = LinearLayoutManager(this)
        adapter = VideogameAdapter(recyclerGames)
        rvVideogames.adapter = adapter
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun updateGames(games : List<Game>) {
        recyclerGames = games
    }

    override fun updateRecycler() {
        Log.d("PideJuegos", "Updating Recycler")
        adapter.videogames = recyclerGames
        adapter.notifyDataSetChanged();
    }

    override fun onDestroy() {
        super.onDestroy()
        TODO("Guardar historial")
    }
}
