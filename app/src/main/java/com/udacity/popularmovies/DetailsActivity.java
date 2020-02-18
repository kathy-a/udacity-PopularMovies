package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.udacity.popularmovies.model.Movie;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView movieTitle = findViewById(R.id.movie_title);

        Intent intent = getIntent();

        if(intent.hasExtra("movieOriginalTitle")){

            movieTitle.setText(intent.getStringExtra("movieOriginalTitle"));
            movieTitle.setText(intent.getStringExtra("moviePoster"));
            movieTitle.setText(intent.getStringExtra("moviePlotSynopsis"));
            movieTitle.setText(intent.getStringExtra("movieUserRating"));
            movieTitle.setText(intent.getStringExtra("movieReleaseDate"));



        }



    }
}
