package com.udacity.popularmovies.network;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.udacity.popularmovies.App;
import com.udacity.popularmovies.MainActivity;
import com.udacity.popularmovies.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MovieService {

    private static final String APIKEY = App.getAppResources().getString(R.string.movie_db_api_key);

    // Create URL for movie db
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

        String TAG = "Popular movies.network";
        Log.v(TAG, "Built URI: " + url);

        return url;
    }


    public static URL buildPosterPathUrl(String posterPath){

        // Create URI for the movie's poster path
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(posterPath.substring(1))
                .build();

        URL url = null;

        try{
            url = new URL(builder.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
