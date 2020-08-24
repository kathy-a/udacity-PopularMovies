package com.udacity.popularmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.udacity.popularmovies.database.AppRepository
import com.udacity.popularmovies.database.MovieEntity

class MainViewModel(application: Application) : AndroidViewModel(application) {
    @JvmField
    var movieData: LiveData<List<MovieEntity>>
    private val mRepository: AppRepository = AppRepository.getInstance(application.applicationContext)

    init {

        // Get data from repository instead of declaring in view model
        movieData = mRepository.mMovies
    }
}