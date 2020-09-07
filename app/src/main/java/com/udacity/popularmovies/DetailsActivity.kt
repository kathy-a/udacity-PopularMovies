package com.udacity.popularmovies

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.annotation.WorkerThread
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
import com.udacity.popularmovies.model.MovieTrailer
import com.udacity.popularmovies.model.Result
import com.udacity.popularmovies.model.ReviewDetails
import com.udacity.popularmovies.model.TrailerDetails
import com.udacity.popularmovies.network.AssertConnectivity
import com.udacity.popularmovies.network.MovieService.retrofitInstance
import com.udacity.popularmovies.network.TheMovieDBService
import com.udacity.popularmovies.ui.MovieReviewAdapter
import com.udacity.popularmovies.ui.MovieTrailerAdapter
import com.udacity.popularmovies.utilies.App
import com.udacity.popularmovies.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import java.util.*

// This class displays the movie details screen after clicking any poster thumbnail on Movies grid view
class DetailsActivity : AppCompatActivity() {

    // data binding
    var mBinding: ActivityDetailsBinding? = null
    private var mViewModel: DetailViewModel? = null
    val mService: TheMovieDBService? = retrofitInstance?.create(TheMovieDBService::class.java)
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
            CoroutineScope(Dispatchers.IO).launch { 
                callWebMovieTrailer(movieDetails.id)
            }

/*            movieTrailerRetrofit(movieSelected.getId());
            movieReviewRetrofit(movieSelected.getId());*/
        }


        // ADD LISTENER TO TRAILER LIVE DATA & set Trailer UI. Listed here for easier view
        mViewModel?.mLiveTrailer?.observe(this, androidx.lifecycle.Observer {
            initTrailerRecyclerView(it as ArrayList<TrailerDetails>)
        })

    }

    /**
     * HANDLE MOVIE TRAILER RetrofitInstance interface & set the URL
     */
    @WorkerThread
    suspend fun callWebMovieTrailer(movieId: Int) {
        val trailerList: ArrayList<TrailerDetails>? = mService?.getTrailer(movieId, API_KEY)?.results

        trailerList?.let {
            for (i in 0 until it.size){
                Log.d(TAG, "callWebMovieTrailer: ${it[i].name}")
                val videoKey = it[i].key
                it[i].key = MOVIE_BASE_URL + videoKey;
            }
            mViewModel?.mLiveTrailer?.postValue(it)
        }

    }



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