package com.udacity.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.MovieTrailer;
import com.udacity.popularmovies.model.TrailerDetails;
import com.udacity.popularmovies.network.MovieService;
import com.udacity.popularmovies.network.TheMovieDBService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// This class displays the movie details screen after clicking any poster thumbnail on Movies grid view
public class DetailsActivity extends AppCompatActivity {

    private static final String MOVIE_ORIGINAL_TITLE = "movieOriginalTitle" ;
    private static final String MOVIE_BASE_URL = "https://www.youtube.com/watch?v=" ;


    //TODO: Pass the API key instead of calling the resource?
    private static final String APIKEY = App.getAppResources().getString(R.string.movie_db_api_key);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to go one level back
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        displayMovieDetails();

    }


    private void displayMovieDetails(){
        TextView movieTitle = findViewById(R.id.text_movie_title);
        ImageView moviePoster = findViewById(R.id.image_movie_poster);
        TextView releaseDate = findViewById(R.id.text_release_date);
        TextView userRating = findViewById(R.id.text_user_rating);
        TextView moviePlotSynopsis = findViewById(R.id.text_movie_plot_synopsis);

        // Get content from main activity and display
        Intent intent = getIntent();
        if((intent != null) && (intent.hasExtra(MOVIE_ORIGINAL_TITLE))){
            movieTitle.setText(intent.getStringExtra("movieOriginalTitle"));

            Picasso.with(this)
                    .load(intent.getStringExtra("moviePoster"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(moviePoster);

            String rating = intent.getStringExtra("movieUserRating") + "/10";

            releaseDate.setText(intent.getStringExtra("movieReleaseDate"));
            userRating.setText(rating);
            moviePlotSynopsis.setText(intent.getStringExtra("moviePlotSynopsis"));

            int movieId = intent.getIntExtra("movieId",0 );

            detailRetrofit(movieId);
            // TODO: Insert connectivity check
        }

    }



    // TODO: FUTURE: combine handling of retrofit instance in separate class
    // Create handle for the RetrofitInstance interface
    private void detailRetrofit(int movieId){
        TheMovieDBService service2 = MovieService.getRetrofitInstance().create(TheMovieDBService.class);

        //int id = 495764;
        Call<MovieTrailer> call = service2.geTrailer(movieId, APIKEY);

        call.enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                if(response.isSuccessful()){
                    Log.d("Trailer onResponse", "Response Successful for movie trailer");

                    ArrayList<TrailerDetails> trailerDetails;
                    trailerDetails = response.body().getResults();


                    // Create URL for movie trailers
                    ArrayList<String> videoUrl = new ArrayList<>();
                    for(int i =0; i < trailerDetails.size(); i++){
                        String videoKey = trailerDetails.get(i).getKey();
                        videoUrl.add(MOVIE_BASE_URL + videoKey);

                        trailerDetails.get(i).setKey(MOVIE_BASE_URL + videoKey);

                    }

                    //TODO: Check how to display the data in what UI

/*                    response.body().setUrl(videoUrl);
                    Log.d("response url", response.body().getUrl().get(0));
                    Log.d("response url", response.body().getUrl().get(1));*/

                    initTrailerRecyclerView(trailerDetails);
                    //displayTrailer(videoUrl);

                }else{
                    Log.d("on Response", "Response Fail for movie trailer");
                }
            }

            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error getting response for movie trailer", Toast.LENGTH_LONG).show();

            }
        });
    }


    private void displayTrailer(ArrayList<String> videoUrl){
        Log.d("display Trailer", videoUrl.get(0) );


    }

    //TODO: adjust the parameter to use trailer object instead
    // Display movie trailer via recyclerview
    private void initTrailerRecyclerView(ArrayList<TrailerDetails> trailerDetails){
        RecyclerView recyclerView = findViewById(R.id.recycler_DetailActivity);
        MovieTrailerAdapter adapter = new MovieTrailerAdapter(this, trailerDetails);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(adapter);
    }

}
