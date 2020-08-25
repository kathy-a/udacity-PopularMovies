package com.udacity.popularmovies;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.database.MovieEntity;
import com.udacity.popularmovies.databinding.ActivityDetailsBinding;
import com.udacity.popularmovies.model.MovieReview;
import com.udacity.popularmovies.model.MovieTrailer;
import com.udacity.popularmovies.model.Result;
import com.udacity.popularmovies.model.ReviewDetails;
import com.udacity.popularmovies.model.TrailerDetails;
import com.udacity.popularmovies.network.AssertConnectivity;
import com.udacity.popularmovies.network.MovieService;
import com.udacity.popularmovies.network.TheMovieDBService;
import com.udacity.popularmovies.ui.MovieReviewAdapter;
import com.udacity.popularmovies.ui.MovieTrailerAdapter;
import com.udacity.popularmovies.utilies.App;
import com.udacity.popularmovies.viewmodel.DetailViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// This class displays the movie details screen after clicking any poster thumbnail on Movies grid view
public class DetailsActivity extends AppCompatActivity {

    private static MovieEntity movieSelected = new MovieEntity();
    private static final String API_KEY = App.getAppResources().getString(R.string.movie_db_api_key);
    public static final String MOVIE_BASE_URL = "https://www.youtube.com/watch?v=" ;
    private static final String TAG = "DetailsActivity";

    // data binding
    ActivityDetailsBinding mBinding;

    private DetailViewModel mViewModel;
    private TheMovieDBService mService = MovieService.getRetrofitInstance().create(TheMovieDBService.class);
    private ToggleButton toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to go one level back
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set new context to Details Activity
        new AssertConnectivity(DetailsActivity.this);

        toggle =  findViewById(R.id.toggle_favorite);

        checkMovieFavorite();
        initViewModel();
        displayMovieDetails();

    }

    public static MovieEntity getMovieDetails() {
        return movieSelected;
    }


    private void setMovieDetails(){
        Intent intent = getIntent();

        if(intent != null){
            movieSelected = (MovieEntity) intent.getSerializableExtra("movies");

        }else{
            Log.d(TAG, "Intent null");
        }
        

    }


/*
    Check if favorite button is toggled to be favorite or not
*/
    private void checkMovieFavorite() {
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    addMovieData();
                } else {
                    deleteMovieData();
                }
            }
        });
    }

    private void deleteMovieData() {
        mViewModel.deleteMovie(movieSelected);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(DetailViewModel.class);

        setMovieDetails();

         mViewModel.loadMovie(movieSelected.getId());

         mViewModel.mLiveMovie.observe(this, new Observer<MovieEntity>() {
             @Override
             public void onChanged(MovieEntity movieEntity) {
                 // Change the favorite toggle button state depending if movie is found in database or not
                 if (movieEntity != null){
                     Log.d("initViewModel", "movie found");
                    toggle.setChecked(true);
                 }else{
                     toggle.setChecked(false);
                 }
             }
         });

    }


    private void addMovieData() {
        mViewModel.addMovieData();
    }


    private void displayMovieDetails(){
        ImageView moviePoster = findViewById(R.id.image_movie_poster);

        // Display the movies in the different views
        mBinding.setMovie(movieSelected);

        Picasso.get()
                .load(movieSelected.getPoster())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(moviePoster);


        // Get the trailer and review views if there is connectivity
        if (AssertConnectivity.isOnline()){
/*            movieTrailerRetrofit(movieSelected.getId());
            movieReviewRetrofit(movieSelected.getId());*/

        }
    }



    // TODO: FUTURE: combine handling of retrofit instance in separate class
    // Create handle for the movie trailer RetrofitInstance interface
/*
    private void movieTrailerRetrofit(int movieId){

        Call<MovieTrailer> call = mService.getTrailer(movieId, API_KEY);

        call.enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                if(response.isSuccessful()){
                    Log.d("Trailer onResponse", "Response Successful for movie trailer");

                    ArrayList<TrailerDetails> trailerDetails;
                    trailerDetails = response.body().results;

                    // Create URL for movie trailers
                    for(int i =0; i < trailerDetails.size(); i++){
                        String videoKey = trailerDetails.get(i).key;
                        trailerDetails.get(i).key = MOVIE_BASE_URL + videoKey;
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
*/


    // TODO: FUTURE: combine handling of retrofit instance in separate class
    // Create handle for the movie review RetrofitInstance interface
/*
    private void movieReviewRetrofit(int movieId){

        Call<MovieReview> call = mService.getReview(movieId, API_KEY);

        call.enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                if(response.isSuccessful()){
                    Log.d("Review onResponse", "Response Successful for movie review");

                    ArrayList<ReviewDetails> reviewDetails;
                    reviewDetails = response.body().results;

                    if(reviewDetails != null){
                        ArrayList<String> review= new ArrayList<>();

                        for(int i =0; i < reviewDetails.size(); i++){
                            String currentReview = reviewDetails.get(i).content;
                            Log.d("Review onResponse", currentReview);
                            review.add(currentReview);
                        }

                        initReviewRecyclerView(reviewDetails);

                    }


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
*/




    // Display movie trailer via recyclerview
    private void initTrailerRecyclerView(ArrayList<TrailerDetails> trailerDetails){
        RecyclerView recyclerView = findViewById(R.id.recycler_DetailActivity_trailer);
        MovieTrailerAdapter adapter = new MovieTrailerAdapter(this, trailerDetails);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
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
