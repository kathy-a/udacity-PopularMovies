package com.udacity.popularmovies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.udacity.popularmovies.database.AppRepository
import com.udacity.popularmovies.database.AppRepository.Companion.getInstance
import com.udacity.popularmovies.database.MovieEntity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: AppRepository?
    private val executor: Executor = Executors.newSingleThreadExecutor()
    var mLiveMovie = MutableLiveData<MovieEntity?>()
    fun addMovieData() {
        mRepository?.addMovieData()
    }

    fun loadMovie(movieId: Int) {
        executor.execute {
            val movie = mRepository?.getMovieById(movieId)

            // Check if movie exist in database
            if (movie == null) {
                Log.d("load movie runnable", "movie null")
            } else {
                Log.d("load movie runnable", "movie found")
                mLiveMovie.postValue(movie)
            }
        }
    }

    fun deleteMovie(movie: MovieEntity?) {
        // mRepository.deleteMovie(mLiveMovie.getValue());
        mRepository!!.deleteMovie(movie)
        mLiveMovie.postValue(null)
    }

    init {

        // Get data from repository instead of declaring in view model
        mRepository = getInstance(application.applicationContext)
    }
}