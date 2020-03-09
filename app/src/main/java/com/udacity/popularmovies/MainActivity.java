package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.popularmovies.network.MovieService.buildPosterPathUrl;

public class MainActivity extends AppCompatActivity {

    private String mSortOrder = "popularity.desc";
    private static final String API_KEY = App.getAppResources().getString(R.string.movie_db_api_key);

    // For Sample Data
    private ArrayList<MovieEntity> movieData = new ArrayList<>();

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: REMOVE comment for init view model
        // initViewModel();

        // TODO: Check if this is required to be added in asynctask because or ROOM implementation change
        // TODO: remove comment tag once local content is settled
        // Call movieDbQueryTask if there is connectivity. Otherwise, display error toast message
/*        new AssertConnectivity(MainActivity.this);

        if(AssertConnectivity.isOnline()){
            initRetrofit(mSortOrder);
        }else
            AssertConnectivity.errorConnectMessage(App.getAppResources().getString(R.string.error_connection_themoviedb));*/


        // For sample data
/*        movieData.addAll(mViewModel.movieData);
        for (MovieEntity currentMovie : movieData) {
            Log.d("MOVIE SAMPLE DATA", currentMovie.toString());
        }*/
        // TODO: Remove the adding of sample data in database
        // Add sample data on db
        //addSampleData();


        // TODO: REMOVE HARDCODED SAMPLE DATA
        ArrayList<MovieEntity> movieList;
        movieList = SampleData.getSampleMovieData();
        initLocalRecyclerView(movieList);


    }




    private void addSampleData() {
        mViewModel.addSampleData();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
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
        }else
            return super.onOptionsItemSelected(item);

        // TODO: Check if this is required to be added in asynctask because or ROOM implementation change
        if(AssertConnectivity.isOnline()){
            initRetrofit(mSortOrder);
        }else
            AssertConnectivity.errorConnectMessage(App.getAppResources().getString(R.string.error_connection_themoviedb));
        return true;
    }

    // TODO: add 1 method that will check what recyclerview will be passed
    // Display movie poster path via recyclerview
    private void initRecyclerView(ArrayList<Result> movieList){
        RecyclerView recyclerView = findViewById(R.id.recycler_MainActivity);
        MoviesViewAdapter adapter = new MoviesViewAdapter(this, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    private void initLocalRecyclerView(ArrayList<MovieEntity> movieList){
        RecyclerView recyclerView = findViewById(R.id.recycler_MainActivity);
        MoviesViewAdapter adapter = new MoviesViewAdapter(this, movieList, true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
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
