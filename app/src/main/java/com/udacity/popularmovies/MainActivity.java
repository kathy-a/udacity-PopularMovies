package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.network.MoviesAPIService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: CLEAN UP UI if needed or not
        TextView textView = findViewById(R.id.grid_movie);

/*        Movie movie = new Movie();
        movie.setOriginalTitle("Harry Potter");

        String movieTitle = movie.getOriginalTitle();
        textView.setText(movieTitle);*/

        //TODO: Add internet connection check in here or when calling moviedb api or in display movies ?
        displayMovies();

/*        String key = getString(R.string.movie_db_api_key);
        textView.setText(key);*/


    }

    private void displayMovies(){

        String JSON = MoviesAPIService.getMoviesJSON( "popularity.desc");
    }



}
