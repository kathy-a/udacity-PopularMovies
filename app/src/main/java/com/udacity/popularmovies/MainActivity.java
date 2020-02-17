package com.udacity.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.network.MovieService;
import com.udacity.popularmovies.utils.JsonUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Add internet connection check in here or when calling moviedb api or in display movies ?

        String sortOrder = "popularity.desc";
        new movieDbQueryTask().execute(sortOrder);



    }

    public class movieDbQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... sortOrder){

            // Build URL
            URL url = MovieService.buildUrl(sortOrder[0]);

            System.out.println(url.toString());
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

}
