package com.udacity.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.DetailsActivity;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.database.MovieEntity;
import com.udacity.popularmovies.model.Result;

import java.util.ArrayList;


// This class is customized recyclerview adapter for movie posters gridview
public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {

    private static final String TAG = "MoviesViewAdapter";

    private Context mContext;
    private ArrayList<Result> mMovies;
    private ArrayList<MovieEntity> mLocalMovies;
    private boolean isLocal;


    public MoviesViewAdapter(Context mContext, ArrayList<MovieEntity> mLocalMovies, boolean isLocal) {
        this.mContext = mContext;
        this.mLocalMovies = mLocalMovies;
        this.isLocal = isLocal;
    }


    public MoviesViewAdapter(Context mContext, ArrayList<Result> movies) {
        this.mMovies = movies;
        this.mContext = mContext;
        this.isLocal = false;
        // TODO: MAY need to set islocal to false for not hardcoded view
    }




    // Required for RecyclerView. Responsible for inflating the view / recycling the viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_poster, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Required for RecyclerView. Changes depends on what layouts are
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String moviePoster;

        if(isLocal){
            moviePoster = mLocalMovies.get(position).getPoster();
        }else{
            moviePoster = mMovies.get(position).getPosterPath();
        }

        Log.d("Poster", moviePoster);

        Picasso.with(mContext)
                .load(moviePoster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageMoviePoster);

    }

    // Required for RecyclerView
    @Override
    public int getItemCount() {
        if(isLocal){
            return mLocalMovies != null? mLocalMovies.size() : 0;

        }else{
            return mMovies != null? mMovies.size() : 0;
        }

    }

    // Holds widget in memory for each individual entry
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageMoviePoster;
        RelativeLayout parentLayout;

        //Constructor required for Viewholder
        public ViewHolder( View itemView) {
            super(itemView);
            imageMoviePoster = itemView.findViewById(R.id.image_movie);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(this);
        }


/*
        Upon clicking any grid view cell, DetailsActivity will be launch & passed with movie details
*/
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            Class destinationActivity = DetailsActivity.class;

            Intent intent = new Intent(mContext, destinationActivity);

            String originalTitle, poster, plotSynopsis, userRating, releaseDate;
            int id;

            if(isLocal){
                originalTitle = mLocalMovies.get(position).getOriginalTitle();
                poster = mLocalMovies.get(position).getPoster();
                plotSynopsis = mLocalMovies.get(position).getPlotSynopsis();
                userRating = mLocalMovies.get(position).getUserRating();
                releaseDate = mLocalMovies.get(position).getReleaseDate();
                id = mLocalMovies.get(position).getId();
                
            }else{
                originalTitle = mMovies.get(position).getOriginalTitle();
                poster = mMovies.get(position).getPosterPath();
                plotSynopsis = mMovies.get(position).getOverview();
                userRating = mMovies.get(position).getVoteAverage().toString();
                releaseDate = mMovies.get(position).getReleaseDate();
                id = mMovies.get(position).getId();
            }


            // Pass the movie details to details activity
            intent.putExtra("movieOriginalTitle", originalTitle);
            intent.putExtra("moviePoster", poster);
            intent.putExtra("moviePlotSynopsis",plotSynopsis);
            intent.putExtra("movieUserRating", userRating );
            intent.putExtra("movieReleaseDate", releaseDate);
            intent.putExtra("movieId", id);

            mContext.startActivity(intent);
        }
    }



}