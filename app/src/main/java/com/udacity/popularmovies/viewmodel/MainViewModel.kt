package com.udacity.popularmovies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.popularmovies.database.AppRepository
import com.udacity.popularmovies.database.MovieEntity
import com.udacity.popularmovies.model.Result

class MainViewModel(application: Application) : AndroidViewModel(application) {
     var localMovieData:  LiveData<List<MovieEntity>>


    var initialMovieData = MutableLiveData<List<Result>>()

    private val mRepository: AppRepository = AppRepository.getInstance(application.applicationContext)

    init {

        // Get data from repository instead of declaring in view model
        //localMovieData = mRepository.mMovies
       var test: MovieEntity
        //test.originalTitle = "TEST"

        localMovieData = mRepository.mMovies
        Log.d(TAG, "Repository movies: ${localMovieData.value}")
        //Log.d(Companion.TAG, ": ${localMovieData.value}")

    }

    companion object {
        // @JvmField
         //var localMovieData: LiveData<List<MovieEntity>>
        private const val TAG = "MainViewModel"
    }
}

private fun <T> MutableLiveData<T>.postValue(mMovies: LiveData<T>) {

}
