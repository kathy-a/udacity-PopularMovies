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
    private ArrayList<Movie> movieList2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: CLEAN UP UI if needed or not
        //TextView textView = findViewById(R.id.grid_movie);

/*        Movie movie = new Movie();
        movie.setOriginalTitle("Harry Potter");

        String movieTitle = movie.getOriginalTitle();
        textView.setText(movieTitle);*/

        //TODO: Add internet connection check in here or when calling moviedb api or in display movies ?

/*        String key = getString(R.string.movie_db_api_key);
        textView.setText(key);*/

        new movieDbQueryTask().execute();

        //initMovieData();

    }

    public class movieDbQueryTask extends AsyncTask<URL, Void, Void> {

        @Override
        protected Void doInBackground(URL... params){

            // Build URL
            URL url = MovieService.buildUrl( "popularity.desc");
            String json = "";

            // Get json response from movie db
            try {
                json = MovieService.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayMovies(json);

            return null;
        }
    }


    public void displayMovies(String json){
        System.out.println(json);
        Log.d("display moves",json);

        movieList = JsonUtils.parseMovieJson(json);

        System.out.println(movieList);
        // TODO: Parse JSON data
        // TODO: Display movies in recycler view

        initRecyclerView();

    }

    private void initMovieData(){
        movieList2.add(new Movie ("title 1", "https://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "1", "1", "1"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185//z7FCF54Jvzv9Anxyf82QeqFXXOO.jpg", "2", "2", "2"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185/7GsM4mtM0worCtIVeiQt28HieeN.jpg", "2", "2", "2"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg", "2", "2", "2"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185/iZf0KyrE25z1sage4SYFLCCrMi9.jpg", "2", "2", "2"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185/y95lQLnuNKdPAzw9F9Ab8kJ80c3.jpg", "2", "2", "2"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg", "2", "2", "2"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg", "2", "2", "2"));
        movieList2.add(new Movie ("title 2", "https://image.tmdb.org/t/p/w185/6ApDtO7xaWAfPqfi2IARXIzj8QS.jpg", "2", "2", "2"));


        initRecyclerView();
    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_MainActivity);
        //MoviesViewAdapter adapter = new MoviesViewAdapter(this, movieList2);
        MoviesViewAdapter adapter = new MoviesViewAdapter(this, movieList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


    }

}
