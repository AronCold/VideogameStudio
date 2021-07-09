package com.example.videogamestudio.model

import proto.Genre
import proto.InvolvedCompany
import proto.Platform
import java.io.Serializable
import java.util.*

class Videogame(
    val name:String, val releaseDate:Date,
    val genres: List<Genre>, val involved_companies: MutableList<InvolvedCompany>,
    val platforms: MutableList<Platform>, val image:String,
    val aggregated_rating: Double, val summary: String) : Serializable
{
}
