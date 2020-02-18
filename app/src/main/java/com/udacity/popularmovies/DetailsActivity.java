package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.Movie;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView movieTitle = findViewById(R.id.movie_title);
        ImageView moviePoster = findViewById(R.id.image_movie_poster);


        Intent intent = getIntent();

        if(intent.hasExtra("movieOriginalTitle")){

            movieTitle.setText(intent.getStringExtra("movieOriginalTitle"));

            Picasso.with(this)
                    .load(intent.getStringExtra("moviePoster"))
                    .into(moviePoster);


/*            movieTitle.setText(intent.getStringExtra("moviePlotSynopsis"));
            movieTitle.setText(intent.getStringExtra("movieUserRating"));
            movieTitle.setText(intent.getStringExtra("movieReleaseDate"));*/



        }



    }
}
