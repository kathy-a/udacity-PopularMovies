package com.udacity.popularmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TrailerDetails : Serializable {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null

    @SerializedName("iso_3166_1")
    @Expose
    var iso31661: String? = null

    @SerializedName("key")
    @Expose
    lateinit var key: String

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("site")
    @Expose
    var site: String? = null

    @SerializedName("size")
    @Expose
    var size: Int? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

}