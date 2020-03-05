package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.TrailerDetails;
import com.udacity.popularmovies.network.AssertConnectivity;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Intent.ACTION_VIEW;

// TODO: SEARCH IF MULTIPLE RECYCLERVIEW IS NORMAL IMPLEMENTATION
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TrailerDetails> trailer;

    public MovieTrailerAdapter(Context mContext, ArrayList<TrailerDetails> trailer){
        this.mContext = mContext;
        this.trailer = trailer;
    }


    // Required for RecyclerView. Responsible for inflating the view / recycling the viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_trailer, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Required for RecyclerView. Changes depends on what layouts are
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.trailerTitle.setText(trailer.get(position).getName());

    }

    @Override
    public int getItemCount() { return trailer != null? trailer.size() : 0; }


    // Holds widget in memory for each individual entry
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trailerTitle;
        RelativeLayout parentLayout;

        //Constructor required for Viewholder
        public ViewHolder( View itemView) {
            super(itemView);
            trailerTitle = itemView.findViewById(R.id.text_trailerTitle);
            parentLayout = itemView.findViewById(R.id.trailerParent_layout);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {

            // TODO: IMPLEMENT ON CLICK of movie trailer and add comment for the method usage
            // Add error handling in case there is no internet

            if(AssertConnectivity.isOnline()){
                int position = getAdapterPosition();
                // Get URL and open the trailer link
                Uri uri = Uri.parse(trailer.get(position).getKey());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }else
                AssertConnectivity.errorConnectMessage(App.getAppResources().getString(R.string.error_connection_general));





        }
    }



}
