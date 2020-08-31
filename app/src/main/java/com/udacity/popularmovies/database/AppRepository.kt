package com.udacity.popularmovies.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.popularmovies.DetailsActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppRepository private constructor(context: Context) {
    var mMovies: LiveData<List<MovieEntity>>
    private val mDb: AppDatabase

    /*    All room database operations must be executed in a background thread
    One executor use for all db so that it has a queue if there are multiple db request*/
    private val executor: Executor = Executors.newSingleThreadExecutor()

    // Get data from DB
    private val allMovies: LiveData<List<MovieEntity>>
        private get() = mDb.movieDao().all

    fun addMovieData() {
        val movie: MovieEntity = DetailsActivity.movieDetails
        executor.execute { mDb.movieDao().insertMovie(movie) }
    }

    fun getMovieById(movieId: Int): MovieEntity? {
        return mDb.movieDao().getMovieById(movieId)
    }

    fun deleteMovie(movie: MovieEntity?) {
        executor.execute { mDb.movieDao().deleteMovie(movie) }
    }

    companion object {
        private var ourInstance: AppRepository? = null
        private const val TAG = "AppRepository"
        @JvmStatic
        fun getInstance(context: Context): AppRepository? {
            if (ourInstance == null) {
                ourInstance = AppRepository(context)
            }
            return ourInstance
        }
    }

    init {
        mDb = AppDatabase.getInstance(context)
        mMovies = allMovies
        Log.d(TAG, "AppRepository: local data " + mMovies.value)
    }
}