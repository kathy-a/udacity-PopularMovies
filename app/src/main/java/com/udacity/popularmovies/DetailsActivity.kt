package com.udacity.popularmovies

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.popularmovies.DetailsActivity
import com.udacity.popularmovies.database.MovieEntity
import com.udacity.popularmovies.databinding.ActivityDetailsBinding
import com.udacity.popularmovies.model.ReviewDetails
import com.udacity.popularmovies.model.TrailerDetails
import com.udacity.popularmovies.network.AssertConnectivity
import com.udacity.popularmovies.network.MovieService.retrofitInstance
import com.udacity.popularmovies.network.TheMovieDBService
import com.udacity.popularmovies.ui.MovieReviewAdapter
import com.udacity.popularmovies.ui.MovieTrailerAdapter
import com.udacity.popularmovies.utilies.App
import com.udacity.popularmovies.viewmodel.DetailViewModel
import java.util.*

// This class displays the movie details screen after clicking any poster thumbnail on Movies grid view
class DetailsActivity : AppCompatActivity() {

    // data binding
    var mBinding: ActivityDetailsBinding? = null
    private var mViewModel: DetailViewModel? = null
    private val mService = retrofitInstance!!.create(TheMovieDBService::class.java)
    private var toggle: ToggleButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        val actionBar = this.supportActionBar

        // Set the action bar back button to go one level back
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Set new context to Details Activity
        AssertConnectivity(this@DetailsActivity)
        toggle = findViewById(R.id.toggle_favorite)
        checkMovieFavorite()
        initViewModel()
        displayMovieDetails()
    }

    private fun setMovieDetails() {
        val intent = intent
        if (intent != null) {
            movieDetails = intent.getSerializableExtra("movies") as MovieEntity
        } else {
            Log.d(TAG, "Intent null")
        }
    }

    /*
    Check if favorite button is toggled to be favorite or not
*/
    private fun checkMovieFavorite() {
        toggle?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                addMovieData()
            } else {
                deleteMovieData()
            }
        }
    }

    private fun deleteMovieData() {
        mViewModel!!.deleteMovie(movieDetails)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)
                .get(DetailViewModel::class.java)
        setMovieDetails()

        //TODO: CHECK HOW TO HANDLE PROPERLY
        mViewModel?.loadMovie(movieDetails.id)
        mViewModel?.mLiveMovie?.observe(this, { movieEntity -> // Change the favorite toggle button state depending if movie is found in database or not
            if (movieEntity != null) {
                Log.d("initViewModel", "movie found")
                toggle!!.isChecked = true
            } else {
                toggle!!.isChecked = false
            }
        })
    }

    private fun addMovieData() {
        mViewModel?.addMovieData()
    }

    private fun displayMovieDetails() {
        val moviePoster = findViewById<ImageView>(R.id.image_movie_poster)

        // Display the movies in the different views
        mBinding!!.movie = movieDetails
        Picasso.get()
                .load(movieDetails.poster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(moviePoster)


        // Get the trailer and review views if there is connectivity
        if (AssertConnectivity.isOnline()) {
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
    private fun initTrailerRecyclerView(trailerDetails: ArrayList<TrailerDetails>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_DetailActivity_trailer)
        val adapter = MovieTrailerAdapter(this, trailerDetails)
        val horizontalLayoutManager = LinearLayoutManager(this@DetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = horizontalLayoutManager
        recyclerView.adapter = adapter
    }

    // Display movie review via recyclerview
    private fun initReviewRecyclerView(reviewDetails: ArrayList<ReviewDetails>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_DetailActivity_review)
        val adapter = MovieReviewAdapter(this, reviewDetails)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val verticalLayoutManager = LinearLayoutManager(this@DetailsActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = verticalLayoutManager
        // Add divider to recyclerview
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                verticalLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
    }

    companion object {
        lateinit var movieDetails: MovieEntity
        private val API_KEY = App.getAppResources().getString(R.string.movie_db_api_key)
        const val MOVIE_BASE_URL = "https://www.youtube.com/watch?v="
        private const val TAG = "DetailsActivity"
    }
}