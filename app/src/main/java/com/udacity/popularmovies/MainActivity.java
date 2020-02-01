package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.udacity.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: CLEAN UP UI if needed or not
        TextView textView = findViewById(R.id.grid_movie);

        Movie movie = new Movie();
        movie.setOriginalTitle("Harry Potter");

        String movieTitle = movie.getOriginalTitle();

        textView.setText(movieTitle);
    }
}
