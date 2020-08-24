package com.udacity.popularmovies.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.popularmovies.DetailsActivity
import com.udacity.popularmovies.R
import com.udacity.popularmovies.database.MovieEntity
import com.udacity.popularmovies.model.Result
import java.util.*

// This class is customized recyclerview adapter for movie posters gridview
class MoviesViewAdapter : RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {
    private var mContext: Context
    private var mMovies: ArrayList<Result>? = null
    private var mLocalMovies: ArrayList<MovieEntity>? = null
    private var isLocal: Boolean

    constructor(mContext: Context, mLocalMovies: ArrayList<MovieEntity>?, isLocal: Boolean) {
        this.mContext = mContext
        this.mLocalMovies = mLocalMovies
        this.isLocal = isLocal
    }

    constructor(mContext: Context, movies: ArrayList<Result>?) {
        mMovies = movies
        this.mContext = mContext
        isLocal = false
    }

    // Required for RecyclerView. Responsible for inflating the view / recycling the viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_poster, parent, false)
        return ViewHolder(view)
    }

    // Required for RecyclerView. Changes depends on what layouts are
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moviePoster: String?
        moviePoster = if (isLocal) {
            mLocalMovies!![position].poster
        } else {
            mMovies!![position].posterPath
        }
        Log.d("Poster", moviePoster)
        Picasso.get()
                .load(moviePoster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageMoviePoster)
    }

    // Required for RecyclerView
    override fun getItemCount(): Int {
        return if (isLocal) {
            if (mLocalMovies != null) mLocalMovies!!.size else 0
        } else {
            if (mMovies != null) mMovies!!.size else 0
        }
    }

    // Holds widget in memory for each individual entry
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var imageMoviePoster: ImageView
        var parentLayout: RelativeLayout?

        /*
        Upon clicking any grid view cell, DetailsActivity will be launch & passed with movie details
*/
        override fun onClick(v: View) {
            val position = adapterPosition
            val destinationActivity: Class<*> = DetailsActivity::class.java
            val intent = Intent(mContext, destinationActivity)
            val movieSelected = MovieEntity()
            if (isLocal) {
                movieSelected.originalTitle = mLocalMovies!![position].originalTitle
                movieSelected.poster = mLocalMovies!![position].poster
                movieSelected.plotSynopsis = mLocalMovies!![position].plotSynopsis
                movieSelected.userRating = mLocalMovies!![position].userRating
                movieSelected.releaseDate = mLocalMovies!![position].releaseDate
                movieSelected.id = mLocalMovies!![position].id
            } else {
                movieSelected.originalTitle = mMovies!![position].originalTitle
                movieSelected.poster = mMovies!![position].posterPath
                movieSelected.plotSynopsis = mMovies!![position].overview
                movieSelected.userRating = mMovies!![position].voteAverage.toString()
                movieSelected.releaseDate = mMovies!![position].releaseDate
                movieSelected.id = mMovies!![position].id!!
            }

            // Pass the movie details to details activity
            intent.putExtra("movies", movieSelected)
            mContext.startActivity(intent)
        }

        //Constructor required for Viewholder
        init {
            imageMoviePoster = itemView.findViewById(R.id.image_movie)
            parentLayout = itemView.findViewById(R.id.parent_layout)
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        private const val TAG = "MoviesViewAdapter"
    }
}