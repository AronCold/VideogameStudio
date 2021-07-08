package com.example.videogamestudio.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.videogamestudio.JuegoActivity
import com.example.videogamestudio.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import proto.Game

class VideogameAdapter(var videogames: List<Game>) :
    RecyclerView.Adapter<VideogameAdapter.VideogameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideogameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideogameHolder(layoutInflater.inflate(R.layout.item_videogame, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VideogameHolder, position: Int) {
        val context=holder.itemView.context
        holder.render(videogames[position])
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val datosApp = Intent(context, JuegoActivity::class.java).apply {
                    val juegoJSONString = Gson().toJson(videogames[position])
                    TODO("Convertir objeto game en Videogame")
                    putExtra("Juego", juegoJSONString)
                }
                context.startActivity(datosApp)
            }
        })
    }

    override fun getItemCount(): Int = videogames.size

    class VideogameHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.O)
        fun render(videogame: Game) {
            view.findViewById<TextView>(R.id.tvName).text = videogame.name

            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
            val netDate = java.util.Date(videogame.firstReleaseDate.seconds*1000)
            val date= sdf.format(netDate)

            view.findViewById<TextView>(R.id.tvReleaseDate).text = date.toString()

            var empresas =""

            videogame.involvedCompaniesList.forEach {
                empresas+=it.company.name+" "
            }

            view.findViewById<TextView>(R.id.tvPublisher).text = empresas

            var coverVG = videogame.cover.url.replace("t_thumb", "t_cover_big")

            Picasso.get().load("https:$coverVG").into(view.findViewById<ImageView>(R.id.ivVideogame))

        }
    }
}