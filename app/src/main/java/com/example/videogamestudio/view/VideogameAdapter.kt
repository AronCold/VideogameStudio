package com.example.videogamestudio.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videogamestudio.R
import com.squareup.picasso.Picasso

class VideogameAdapter(val videogame:List<Videogame>):RecyclerView.Adapter<VideogameAdapter.VideogameHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideogameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideogameHolder(layoutInflater.inflate(R.layout.item_videogame, parent, false))
    }

    override fun onBindViewHolder(holder: VideogameHolder, position: Int) {
        holder.render(videogame[position])
    }

    override fun getItemCount(): Int = videogame.size

    class VideogameHolder(val view: View):RecyclerView.ViewHolder(view){
        fun render(videogame: Videogame){
            view.findViewById<TextView>(R.id.tvName).text=videogame.name
            view.findViewById<TextView>(R.id.tvAge).text=videogame.age
            view.findViewById<TextView>(R.id.tvPublisher).text=videogame.publisher
            view.findViewById<TextView>(R.id.tvConsoles).text=videogame.consoles

            Picasso.get().load(videogame.image).into(view.findViewById<ImageView>(R.id.ivVideogame))

        }
    }
}