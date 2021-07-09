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
import androidx.recyclerview.widget.RecyclerView
import com.example.videogamestudio.JuegoActivity
import com.example.videogamestudio.R
import com.example.videogamestudio.model.Videogame
import com.squareup.picasso.Picasso
import proto.Game

class VideogameAdapter(var videogames: List<Videogame>) :
    RecyclerView.Adapter<VideogameAdapter.VideogameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideogameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideogameHolder(layoutInflater.inflate(R.layout.item_videogame, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VideogameHolder, position: Int) {
        val context = holder.itemView.context
        holder.render(videogames[position])
        holder.itemView.setOnClickListener {
            val datosApp = Intent(context, JuegoActivity::class.java).apply {

                putExtra("Juego", videogames[position])
            }
            context.startActivity(datosApp)
        }
    }

    override fun getItemCount(): Int = videogames.size

    class VideogameHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.O)
        fun render(videogame: Videogame) {
            view.findViewById<TextView>(R.id.tvName).text = videogame.name

            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
            val netDate = videogame.releaseDate
            val date = sdf.format(netDate)

            view.findViewById<TextView>(R.id.tvReleaseDate).text = date.toString()


            var empresas = ""

            videogame.involved_companies.forEach {
                empresas += it.company.name + " ,"
            }

            view.findViewById<TextView>(R.id.tvPublisher).text = empresas

            //hacer tambien con CONSOLAS
            var consolas = ""

            videogame.platforms.forEach {
                consolas += it.name + " ,"
            }

            view.findViewById<TextView>(R.id.tvConsoles).text = consolas


            var coverVG = videogame.image

            Picasso.get().load(coverVG)
                .into(view.findViewById<ImageView>(R.id.ivVideogame))

        }
    }
}