package com.example.videogamestudio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.videogamestudio.model.Videogame

class RelatedSearchActivity : AppCompatActivity() {

    lateinit var test : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_search)

        test = findViewById(R.id.test)

        var videogame = intent.getSerializableExtra("Juego") as Videogame
        test.setText(videogame.name)
    }
}