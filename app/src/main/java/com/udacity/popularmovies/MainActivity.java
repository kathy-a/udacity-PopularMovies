package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.network.MovieService;
import com.udacity.popularmovies.utils.JsonUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private String sortOrder = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call movieDbQueryTask if there is connectivity. Otherwise, display error toast message
        if(isOnline()){
            new movieDbQueryTask().execute(sortOrder);
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
            new movieDbQueryTask().execute(sortOrder);
        }else
            errorConnectMessage();

        return true;
    }

    public class movieDbQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... sortOrder){
            // Build URL
            URL url = MovieService.buildUrl(sortOrder[0]);
            String json = "";

            // Get json response from movie db
            try {
                json = MovieService.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json){
            displayMovies(json);
            initRecyclerView();

        }
    }

    public void displayMovies(String json){
        System.out.println(json);
        Log.d("display moves",json);
        movieList = JsonUtils.parseMovieJson(json);

    }

    private void initRecyclerView(){
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

}
