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



public interface GetMovieService {



    @GET("/3/discover/movie")
    Call <Movies> getData(@Query("api_key") String api_key);



    //Call<ArrayList<Movie>> getData(@Path("api_key") String APIKEY);

    //https://api.themoviedb.org/3/discover/movie?api_key=2feb2d7cef79fb1ffd428e1b74b46176

/*    @Headers("api_key" + APIKEY)
    @GET("/3/discover/movie?sort_by=vote_count.desc&api_key={apiKey}")
    Call<ArrayList<Movie>> getData(@Query("api_key") String apiKey);*/




}
