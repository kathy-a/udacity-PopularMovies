package com.udacity.popularmovies.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.udacity.popularmovies.DetailsActivity;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.database.AppRepository;
import com.udacity.popularmovies.database.MovieEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    private Context mContext;


    private Executor executor = Executors.newSingleThreadExecutor();
    public MutableLiveData<MovieEntity> mLiveMovie =
            new MutableLiveData<>();



    public DetailViewModel(@NonNull Application application) {

        super(application);

        // Get data from repository instead of declaring in view model
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }



    public void addMovieData() {
        mRepository.addMovieData();

    }

    public void loadMovie(final int movieId ) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MovieEntity movie = mRepository.getMovieById(movieId);
                //mLiveMovie.postValue(movie);

                // Check if movie exist in database
                if (movie == null){
                    Log.d("load movie runnable", "movie null");
                }else{
                    Log.d("load movie runnable", "movie found");
                    mLiveMovie.postValue(movie);
                }

            }
        });
    }

    public void deleteMovie(MovieEntity movie) {
       // mRepository.deleteMovie(mLiveMovie.getValue());
        mRepository.deleteMovie(movie);
        mLiveMovie.postValue(null);




    }
}
