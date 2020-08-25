package com.udacity.popularmovies.network

import com.udacity.popularmovies.model.MovieReview
import com.udacity.popularmovies.model.MovieTrailer
import com.udacity.popularmovies.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interface for Retrofit Implementation
interface TheMovieDBService {
    @GET("/3/discover/movie")
    suspend fun getData(
            @Query("api_key") apiKey: String?,
            @Query("sort_by") sortOrder: String?): Movies?
            //@Query("sort_by") sortOrder: String?): Call<Movies?>?


    @GET("/3/movie/{id}/videos")
    suspend fun getTrailer(
            @Path("id") id: Int,
            @Query("api_key") apiKey: String?): Call<MovieTrailer?>?

    @GET("/3/movie/{id}/reviews")
    suspend fun getReview(
            @Path("id") id: Int,
            @Query("api_key") apiKey: String?): Call<MovieReview?>?
}