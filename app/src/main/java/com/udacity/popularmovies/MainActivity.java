package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.udacity.popularmovies.model.Movies;
import com.udacity.popularmovies.model.Result;

import com.udacity.popularmovies.network.TheMovieDBService;
import com.udacity.popularmovies.network.MovieService;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.popularmovies.network.MovieService.buildPosterPathUrl;

public class MainActivity extends AppCompatActivity {

    private String sortOrder = "popularity.desc";
    private static final String APIKEY = App.getAppResources().getString(R.string.movie_db_api_key);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call movieDbQueryTask if there is connectivity. Otherwise, display error toast message
        if(isOnline()){
            initRetrofit(sortOrder);
        }else
            errorConnectMessage();

    }

    // Display Menu layout created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sort_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_most_popular){
            sortOrder = "popularity.desc";
        }else if (item.getItemId() == R.id.item_top_rated){
            sortOrder = "vote_count.desc";
        }else
            return super.onOptionsItemSelected(item);

        if(isOnline()){
            initRetrofit(sortOrder);
        }else
            errorConnectMessage();

        return true;
    }


    private void initRecyclerView(ArrayList<Result> movieList){
        RecyclerView recyclerView = findViewById(R.id.recycler_MainActivity);
        MoviesViewAdapter adapter = new MoviesViewAdapter(this, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void errorConnectMessage() {
        Toast.makeText(this, R.string.error_connection, Toast.LENGTH_LONG).show();
    }

    // Create handle for the RetrofitInstance interface
    public void initRetrofit(String sortOrder){

        TheMovieDBService service = MovieService.getRetrofitInstance().create(TheMovieDBService.class);

        Call <Movies> call = service.getData(APIKEY, sortOrder);

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
