package com.udacity.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder>{

    private static final String TAG = "MoviesViewAdapter";

    private ArrayList<Movie> movies;
    private Context mContext;


    public MoviesViewAdapter(Context mContext, ArrayList<Movie> movies) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(movies.get(position).getPoster())
                .into(holder.imageMoviePoster);


        // TODO: Add onclick listener
    }

    // Required for RecyclerView
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Holds widget in memory for each individual entry
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageMoviePoster;
        RelativeLayout parentLayout;

        //Constructor required for Viewholder
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMoviePoster = itemView.findViewById(R.id.image_movie);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }


}