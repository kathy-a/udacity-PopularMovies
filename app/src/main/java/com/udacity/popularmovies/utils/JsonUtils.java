package com.udacity.popularmovies.utils;

import android.util.Log;

import com.udacity.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.udacity.popularmovies.network.MovieService.buildPosterPathUrl;

public class JsonUtils {

    public static ArrayList<Movie> parseMovieJson(String json){

        ArrayList<Movie> movieList = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(json);

            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");

            for(int i = 0; i< moviesArray.length(); i++){
                JSONObject currentMovieDetails = moviesArray.getJSONObject(i);

                String currentOriginalTitle = currentMovieDetails.getString("original_title");
                String currentPlotSynopsis = currentMovieDetails.getString("overview");
                String currentUserRating = currentMovieDetails.getString("vote_average");
                String currentReleaseDate = currentMovieDetails.getString("release_date");

                String posterPath = currentMovieDetails.getString("poster_path");


                String currentPoster = buildPosterPathUrl(posterPath).toString();

                movieList.add(new Movie (currentOriginalTitle, currentPoster, currentPlotSynopsis, currentUserRating, currentReleaseDate));
            }


        }catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing the JSON results", e);
        }


        return movieList;
    }

}
