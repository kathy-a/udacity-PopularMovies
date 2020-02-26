package com.udacity.popularmovies.network;

import com.udacity.popularmovies.App;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.model.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;



public interface TheMovieDBService {

    @GET("/3/discover/movie")
    Call  <Movies> getData(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortOrder);

}
