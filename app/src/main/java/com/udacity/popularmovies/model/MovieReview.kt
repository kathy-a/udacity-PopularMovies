package com.udacity.popularmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class MovieReview {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    lateinit var results: ArrayList<ReviewDetails>


    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

}