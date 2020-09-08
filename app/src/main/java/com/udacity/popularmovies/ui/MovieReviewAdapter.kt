package com.udacity.popularmovies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.popularmovies.R
import com.udacity.popularmovies.model.ReviewDetails
import java.util.*

class MovieReviewAdapter(private val mContext: Context, private val mReview: ArrayList<ReviewDetails>?) : RecyclerView.Adapter<MovieReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.reviewAuthor.text = mReview!![position].author
        holder.reviewContent.text = mReview[position].content
    }

    override fun getItemCount(): Int {
        return mReview?.size ?: 0
    }

    // Holds widget in memory for each individual entry
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var reviewAuthor: TextView = itemView.findViewById(R.id.text_reviewAuthor)
        var reviewContent: TextView = itemView.findViewById(R.id.text_reviewContent)
        var parentLayout: RelativeLayout? = itemView.findViewById(R.id.reviewParent_layout)

    }

}