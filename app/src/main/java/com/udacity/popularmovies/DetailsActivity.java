package com.udacity.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.MovieReview;
import com.udacity.popularmovies.model.MovieTrailer;
import com.udacity.popularmovies.model.ReviewDetails;
import com.udacity.popularmovies.model.TrailerDetails;
import com.udacity.popularmovies.network.AssertConnectivity;
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

    TheMovieDBService service = MovieService.getRetrofitInstance().create(TheMovieDBService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to go one level back
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set new context to Details Activity
        new AssertConnectivity(DetailsActivity.this);

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

            // TODO: Insert connectivity check
            movieTrailerRetrofit(movieId);
            movieReviewRetrofit(movieId);

        }

    }



    // TODO: FUTURE: combine handling of retrofit instance in separate class
    // Create handle for the movie trailer RetrofitInstance interface
    private void movieTrailerRetrofit(int movieId){

        Call<MovieTrailer> call = service.getTrailer(movieId, APIKEY);

        call.enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                if(response.isSuccessful()){
                    Log.d("Trailer onResponse", "Response Successful for movie trailer");

                    ArrayList<TrailerDetails> trailerDetails;
                    trailerDetails = response.body().getResults();

                    // Create URL for movie trailers
                    for(int i =0; i < trailerDetails.size(); i++){
                        String videoKey = trailerDetails.get(i).getKey();
                        trailerDetails.get(i).setKey(MOVIE_BASE_URL + videoKey);
                    }

                    initTrailerRecyclerView(trailerDetails);


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


    // TODO: FUTURE: combine handling of retrofit instance in separate class
    // Create handle for the movie review RetrofitInstance interface
    private void movieReviewRetrofit(int movieId){

        Call<MovieReview> call = service.getReview(movieId, APIKEY);

        call.enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                if(response.isSuccessful()){
                    Log.d("Review onResponse", "Response Successful for movie review");

                    ArrayList<ReviewDetails> reviewDetails;
                    reviewDetails = response.body().getResults();

                    ArrayList<String> review= new ArrayList<>();;



                    for(int i =0; i < reviewDetails.size(); i++){
                        String currentReview = reviewDetails.get(i).getContent();
                        Log.d("Review onResponse", currentReview);
                        review.add(currentReview);
                    }


                    // TODO: INITIALIZE RECYCLERVIEW FOR REVIEW
                    initReviewRecyclerView(reviewDetails);


                }else{
                    Log.d("Review onResponse", "Response Fail for movie review");
                }

            }

            @Override
            public void onFailure(Call<MovieReview> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error getting response for movie review", Toast.LENGTH_LONG).show();
            }
        });




    }




    // Display movie trailer via recyclerview
    private void initTrailerRecyclerView(ArrayList<TrailerDetails> trailerDetails){
        RecyclerView recyclerView = findViewById(R.id.recycler_DetailActivity_trailer);
        MovieTrailerAdapter adapter = new MovieTrailerAdapter(this, trailerDetails);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        // Add divider to recyclerview
/*        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                horizontalLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);*/

        recyclerView.setAdapter(adapter);

    }

    // Display movie review via recyclerview
    private void initReviewRecyclerView(ArrayList<ReviewDetails> reviewDetails){
        RecyclerView recyclerView = findViewById(R.id.recycler_DetailActivity_review);
        MovieReviewAdapter adapter = new MovieReviewAdapter(this, reviewDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManager);
        // Add divider to recyclerview
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                verticalLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);



        recyclerView.setAdapter(adapter);
    }

}
