package com.udacity.popularmovies.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.utilies.App;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.model.ReviewDetails;
import com.udacity.popularmovies.network.AssertConnectivity;

import java.util.ArrayList;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ReviewDetails> review;

    public MovieReviewAdapter(Context mContext, ArrayList<ReviewDetails> review) {
        this.mContext = mContext;
        this.review = review;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_review, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.reviewAuthor.setText(review.get(position).getAuthor());
        holder.reviewContent.setText(review.get(position).getContent());


    }

    @Override
    public int getItemCount() { return review != null? review.size() : 0; }



    // Holds widget in memory for each individual entry
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView reviewAuthor;
        TextView reviewContent;

        RelativeLayout parentLayout;

        //Constructor required for Viewholder
        public ViewHolder( View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.text_reviewAuthor);
            reviewContent = itemView.findViewById(R.id.text_reviewContent);

            parentLayout = itemView.findViewById(R.id.reviewParent_layout);
            itemView.setOnClickListener(this);
        }


        // TODO: CHECK IF SHOULD BE ADDED
        @Override
        public void onClick(View v) {

            // TODO: IMPLEMENT ON CLICK of movie trailer and add comment for the method usage
            // Add error handling in case there is no internet

            if(AssertConnectivity.isOnline()){
                int position = getAdapterPosition();
/*                // Get URL and open the trailer link
                Uri uri = Uri.parse(trailer.get(position).getKey());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);*/
            }else
                AssertConnectivity.errorConnectMessage(App.getAppResources().getString(R.string.error_connection_general));





        }
    }


}
