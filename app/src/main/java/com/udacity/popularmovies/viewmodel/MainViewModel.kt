package com.udacity.popularmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.popularmovies.database.AppRepository
import com.udacity.popularmovies.database.MovieEntity
import com.udacity.popularmovies.model.Result

class MainViewModel(application: Application) : AndroidViewModel(application) {
    @JvmField
    var movieData3: LiveData<List<MovieEntity>>

    var movieData = MutableLiveData<List<Result>>()

    private val mRepository: AppRepository = AppRepository.getInstance(application.applicationContext)

    init {

        // Get data from repository instead of declaring in view model
        movieData3 = mRepository.mMovies
    }
}