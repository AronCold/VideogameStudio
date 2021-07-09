package com.example.videogamestudio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.videogamestudio.model.Videogame
import com.example.videogamestudio.view.MainActivity

class RelatedSearchActivity : AppCompatActivity() {

    lateinit var test : TextView

    lateinit var keywordSpinner  : Spinner
    lateinit var genresSpinner  : Spinner
    lateinit var platformsSpinner  : Spinner

    lateinit var btnRelatedSearch : Button
    lateinit var context : Context
    lateinit var properties : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_search)

        test = findViewById(R.id.test)

        keywordSpinner = findViewById(R.id.spinnerKeyword)
        genresSpinner = findViewById(R.id.spinnerGenre)
        platformsSpinner = findViewById(R.id.spinnerPlatform)

        btnRelatedSearch = findViewById(R.id.btnRelatedSearch)

        val videogame = intent.getSerializableExtra("Juego") as Videogame
        test.setText(videogame.name)

        properties = arrayListOf("Empty", "Empty", "Empty", videogame.id.toString())

        //keywords spinner
        val keywords = videogame.keywords as MutableList<String>
        if (keywords.isEmpty()) keywords.add("Not available")
        Log.d("RelatedSearch", keywords.toString())

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, keywords)
        keywordSpinner.adapter = adaptador
        keywordSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("RelatedSearch", "Item seleccionado: " + keywordSpinner.selectedItem.toString())
                if(!keywordSpinner.selectedItem.toString().equals("Not available"))
                    properties[0] = keywordSpinner.selectedItem.toString()
                Log.d("RelatedSearch", "Properties: $properties")

                propiedadesValida()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        //genres spinner
        val genres = videogame.genres as MutableList<String>
        if (genres.isEmpty()) genres.add("Not available")
        Log.d("RelatedSearch", genres.toString())

        val adaptador2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        genresSpinner.adapter = adaptador2
        genresSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("RelatedSearch", "Item seleccionado: " + genresSpinner.selectedItem.toString())
                if(!genresSpinner.selectedItem.toString().equals("Not available"))
                    properties[1] = genresSpinner.selectedItem.toString()
                Log.d("RelatedSearch", "Properties: $properties")

                propiedadesValida()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        //platforms spinner
        val platforms = videogame.platforms as MutableList<String>
        if (platforms.isEmpty()) platforms.add("Not available")
        Log.d("RelatedSearch", platforms.toString())

        val adaptador3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, platforms)
        platformsSpinner.adapter = adaptador3
        platformsSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("RelatedSearch", "Item seleccionado: " + platformsSpinner.selectedItem.toString())
                if(!platformsSpinner.selectedItem.toString().equals("Not available"))
                    properties[2] = platformsSpinner.selectedItem.toString()
                Log.d("RelatedSearch", "Properties: $properties")

                propiedadesValida()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        context = this.applicationContext

        btnRelatedSearch.setOnClickListener {
            val datosApp = Intent(context, MainActivity::class.java).apply {
                putStringArrayListExtra("Properties", properties)
            }
            startActivity(datosApp)
        }
    }

    fun propiedadesValida(){
        btnRelatedSearch.isEnabled = !(((properties[0] == "Not available")||(properties[0] == "Empty"))
                && ((properties[1] == "Not available")||(properties[1] == "Empty"))
                && ((properties[2] == "Not available")||(properties[2] == "Empty")))
    }
}