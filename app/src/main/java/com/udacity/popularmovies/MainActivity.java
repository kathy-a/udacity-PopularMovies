package com.udacity.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.udacity.popularmovies.model.Movies;
import com.udacity.popularmovies.model.Result;

import com.udacity.popularmovies.network.AssertConnectivity;
import com.udacity.popularmovies.network.TheMovieDBService;
import com.udacity.popularmovies.network.MovieService;
import com.udacity.popularmovies.database.MovieEntity; // For Sample data
import com.udacity.popularmovies.ui.MoviesViewAdapter;
import com.udacity.popularmovies.utilies.App;
import com.udacity.popularmovies.utilies.SampleData;
import com.udacity.popularmovies.viewmodel.MainViewModel;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.popularmovies.network.MovieService.buildPosterPathUrl;

public class MainActivity extends AppCompatActivity {

    private String mSortOrder = "popularity.desc";
    private static final String API_KEY = App.getAppResources().getString(R.string.movie_db_api_key);

    private RecyclerView mRecyclerView;
    private MoviesViewAdapter mAdapter;

    private ArrayList<MovieEntity> movieData = new ArrayList<>();

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            // TODO: Check if this is required to be added in asynctask because or ROOM implementation change
            // TODO: remove comment tag once local content is settled
            // Call movieDbQueryTask if there is connectivity. Otherwise, display error toast message
            new AssertConnectivity(MainActivity.this);

            if(AssertConnectivity.isOnline()){
                initRetrofit(mSortOrder);
            }else
                AssertConnectivity.errorConnectMessage(App.getAppResources().getString(R.string.error_connection_themoviedb));

    }


    private void initViewModel() {
        final Observer<List<MovieEntity>> movieObserver =
                new Observer<List<MovieEntity>>() {

                    @Override
                    public void onChanged(List<MovieEntity> movieEntities) {
                        movieData.clear();
                        movieData.addAll(movieEntities);
                        if(mAdapter == null){
                            mAdapter = new MoviesViewAdapter(MainActivity.this, movieData, true);
                            mRecyclerView.setAdapter(mAdapter);
                        }else{
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mViewModel.movieData.observe(this, movieObserver);
    }

    // Display Menu layout created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sort_order, menu);
        return true;
    }

    // If movie sort order preference is modified, send the request to themoviedb.org and display response
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.item_most_popular){
            mSortOrder = "popularity.desc";
        }else if (item.getItemId() == R.id.item_top_rated){
            mSortOrder = "vote_count.desc";
        }

        if(AssertConnectivity.isOnline()){
            if(item.getItemId() == R.id.item_favorites){
                // TODO: MAY NEED TO ADJUST TO UPDATE THE CLOUD DATA TO BE CALLED IN REPOSITORY
                initLocalRecyclerView();
                initViewModel();
            }else
                initRetrofit(mSortOrder);
        }else{
            AssertConnectivity.errorConnectMessage(App.getAppResources().getString(R.string.error_connection_themoviedb));
            initLocalRecyclerView();
            initViewModel();
        }
        return true;
    }

    // TODO: add 1 method that will check what recyclerview will be passed
    // Display movie poster path via recyclerview
    private void initRecyclerView(ArrayList<Result> movieList){
        mRecyclerView = findViewById(R.id.recycler_MainActivity);
        mAdapter = new MoviesViewAdapter(this, movieList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initLocalRecyclerView(){
        mAdapter = null;
        mRecyclerView = findViewById(R.id.recycler_MainActivity);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }


    // Create handle for the RetrofitInstance interface
    private void initRetrofit(String sortOrder){

        TheMovieDBService service = MovieService.getRetrofitInstance().create(TheMovieDBService.class);

        Call <Movies> call = service.getData(API_KEY, sortOrder);

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if(response.isSuccessful()){
                    Log.d("on Response", "Response Successful");

                    // Set the variable name to movie instead of result for readability
                    ArrayList<Result> movieList;

                    movieList = response.body().getResults();

                    for(int i =0; i < movieList.size(); i++){
                        String posterPath = movieList.get(i).getPosterPath();
                        String currentPoster = buildPosterPathUrl(posterPath).toString();
                        movieList.get(i).setPosterPath(currentPoster); //Change poster path to use the URL
                    }

                    initRecyclerView(movieList);

                }else{
                    Log.d("on Response", "Response Fail");
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error getting response", Toast.LENGTH_LONG).show();
            }
        });


    }

}
