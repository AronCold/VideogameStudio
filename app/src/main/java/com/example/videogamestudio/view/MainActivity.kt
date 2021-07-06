package com.example.videogamestudio.view


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogamestudio.R
import com.example.videogamestudio.model.Model
import com.example.videogamestudio.presenter.Presenter
import proto.Game


class MainActivity : AppCompatActivity(), MainViewVG{

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

    override var games = ArrayList<Videogame>()

    lateinit var presenter: Presenter

    lateinit var rvVideogames : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model= Model(applicationContext)

        presenter=Presenter(this,model)

        rvVideogames= findViewById(R.id.rvVideogames)

        initRecycler()
    }

    private fun initRecycler(){
        rvVideogames.layoutManager =LinearLayoutManager(this)
        val adapter = VideogameAdapter(videogames)
        rvVideogames.adapter=adapter
    }

    override fun showError(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}