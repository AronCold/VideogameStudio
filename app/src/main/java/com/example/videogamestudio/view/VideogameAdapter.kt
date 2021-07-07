package com.example.videogamestudio.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.videogamestudio.R
import com.squareup.picasso.Picasso
import proto.Game

class VideogameAdapter(val videogame: List<Game>) :
    RecyclerView.Adapter<VideogameAdapter.VideogameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideogameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideogameHolder(layoutInflater.inflate(R.layout.item_videogame, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VideogameHolder, position: Int) {
        holder.render(videogame[position])
    }

    override fun getItemCount(): Int = videogame.size

    public updateData(games: List<Game>){
        videogame.clear()
    }

    class VideogameHolder(val view: View) : RecyclerView.ViewHolder(view) {
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


            Picasso.get().load(videogame.cover.url).into(view.findViewById<ImageView>(R.id.ivVideogame))

        }
    }
}