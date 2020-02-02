package com.udacity.popularmovies.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.udacity.popularmovies.App;
import com.udacity.popularmovies.MainActivity;
import com.udacity.popularmovies.R;

public class MoviesAPIService {

    private static final String APIKEY = App.getAppResources().getString(R.string.movie_db_api_key);

    public static String getMoviesJSON(String sortOrder){
        String json = "";

        // TODO: Use sorting variable to choose between most popular & top rated

        // Create URI for the movie list
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", APIKEY)
                .appendQueryParameter("sort_by", sortOrder);

        String URL = builder.build().toString();


        // TODO: CALL METHOD TO GET API RESPONSE & CALL IT IN HERE. PASS THE URI


        //TODO: CLEAN UP LOG
        String TAG = "MoviesAPIService";
        Log.d(TAG,URL);
        System.out.println(URL);

        return json;
    }
}
