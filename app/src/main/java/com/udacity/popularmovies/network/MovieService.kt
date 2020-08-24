package com.udacity.popularmovies.network

import android.net.Uri
import com.udacity.popularmovies.R
import com.udacity.popularmovies.utilies.App
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.MalformedURLException
import java.net.URL

object MovieService {
    private val APIKEY = App.getAppResources().getString(R.string.movie_db_api_key)
    private var sRetrofit: Retrofit? = null
    private const val BASE_URL = "https://api.themoviedb.org"

    // Retrofit Instance for getting JSON response from themoviedb.org
    @JvmStatic
    val retrofitInstance: Retrofit?
        get() {
            if (sRetrofit == null) {
                sRetrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return sRetrofit
        }

    @JvmStatic
    fun buildPosterPathUrl(posterPath: String): URL? {

        // Create URI for the movie's poster path
        val builder = Uri.Builder()
        builder.scheme("https")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(posterPath.substring(1))
                .build()
        var url: URL? = null
        try {
            url = URL(builder.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return url
    }
}