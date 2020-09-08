package com.udacity.popularmovies.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.popularmovies.R
import com.udacity.popularmovies.model.TrailerDetails
import com.udacity.popularmovies.network.AssertConnectivity
import com.udacity.popularmovies.utilies.App
import java.util.*

// TODO: FUTURE SEARCH IF MULTIPLE RECYCLERVIEW IS NORMAL IMPLEMENTATION
class MovieTrailerAdapter(private val mContext: Context, private val mTrailer: ArrayList<TrailerDetails>?) : RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder>() {

    // Required for RecyclerView. Responsible for inflating the view / recycling the viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_trailer, parent, false)
        return ViewHolder(view)
    }

    // Required for RecyclerView. Changes depends on what layouts are
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.trailerTitle.text = mTrailer!![position].name
    }

    override fun getItemCount(): Int {
        return mTrailer?.size ?: 0
    }

    // Holds widget in memory for each individual entry
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var trailerTitle: TextView = itemView.findViewById(R.id.text_trailerTitle)
        var parentLayout: RelativeLayout? = itemView.findViewById(R.id.trailerParent_layout)
        override fun onClick(v: View) {

            // Add error handling in case there is no internet
            if (AssertConnectivity.isOnline()) {
                val position = adapterPosition
                // Get URL and open the trailer link
                val uri = Uri.parse(mTrailer!![position].key)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                mContext.startActivity(intent)
            } else AssertConnectivity.errorConnectMessage(App.getAppResources().getString(R.string.error_connection_general))
        }

        //Constructor required for Viewholder
        init {
            itemView.setOnClickListener(this)
        }
    }

}