package com.example.videogamestudio.model

import proto.Genre
import proto.InvolvedCompany
import proto.Platform
import java.io.Serializable
import java.util.*

class Videogame(
    val name:String, val releaseDate:Date,
    val genres: List<String>, val involved_companies: List<String>,
    val platforms: List<String>, val image:String,
    val aggregated_rating: Double, val summary: String, val id : Int, val keywords : List<String>) : Serializable
{
}
