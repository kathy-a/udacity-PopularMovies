package com.udacity.popularmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class MovieTrailer {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("results")
    @Expose
    lateinit var results: ArrayList<TrailerDetails>
    var url: ArrayList<String>? = null

}