package com.udacity.popularmovies.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.udacity.popularmovies.App;
import com.udacity.popularmovies.MainActivity;
import com.udacity.popularmovies.R;

import java.net.MalformedURLException;
import java.net.URL;

public class MovieService {

    private static final String APIKEY = App.getAppResources().getString(R.string.movie_db_api_key);

    public static URL buildUrl(String sortOrder){

        // Create URI for the movie list
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", APIKEY)
                .appendQueryParameter("sort_by", sortOrder)
                .build();

        URL url = null;

        try{
            url = new URL(builder.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        String TAG = "popularmovies.network";
        Log.v(TAG, "Built URI: " + url);

        return url;
    }
}
