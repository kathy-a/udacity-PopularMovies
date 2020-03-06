package com.udacity.popularmovies.network;

import com.udacity.popularmovies.model.MovieReview;
import com.udacity.popularmovies.model.MovieTrailer;
import com.udacity.popularmovies.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


// Interface for Retrofit Implementation
public interface TheMovieDBService {

    @GET("/3/discover/movie")
    Call  <Movies> getData(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortOrder);

    @GET("/3/movie/{id}/videos")
    Call  <MovieTrailer> getTrailer(
            @Path("id") int id,
            @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/reviews")
    Call  <MovieReview> getReview(
            @Path("id") int id,
            @Query("api_key") String apiKey);



}
