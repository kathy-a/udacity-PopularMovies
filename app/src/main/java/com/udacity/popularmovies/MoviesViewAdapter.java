package com.udacity.popularmovies;

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
import com.udacity.popularmovies.model.Result;

import java.util.ArrayList;


// This class is customized recyclerview adapter for movie posters gridview
public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {

    private static final String TAG = "MoviesViewAdapter";

    private ArrayList<Result> movies;
    private Context mContext;


    public MoviesViewAdapter(Context mContext, ArrayList<Result> movies) {
        this.movies = movies;
        this.mContext = mContext;
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
        Picasso.with(mContext)
                .load(movies.get(position).getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageMoviePoster);

    }

    // Required for RecyclerView
    @Override
    public int getItemCount() {
        return movies != null? movies.size() : 0;
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

            // Pass the movie details to details activity
            intent.putExtra("movieOriginalTitle", movies.get(position).getOriginalTitle());
            intent.putExtra("moviePoster", movies.get(position).getPosterPath());
            intent.putExtra("moviePlotSynopsis", movies.get(position).getOverview());
            intent.putExtra("movieUserRating", movies.get(position).getVoteAverage().toString());
            intent.putExtra("movieReleaseDate", movies.get(position).getReleaseDate());
            intent.putExtra("movieId", movies.get(position).getId());

            mContext.startActivity(intent);
        }
    }



}