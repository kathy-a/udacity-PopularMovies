package com.udacity.popularmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/*
POJO for JSON response from themoviedb.org
*/
class Movies {

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("results")
    @Expose
    lateinit var results: ArrayList<Result>

}