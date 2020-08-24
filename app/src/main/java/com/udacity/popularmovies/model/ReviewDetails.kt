package com.udacity.popularmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReviewDetails {

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("content")
    @Expose
    lateinit var content: String


    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}