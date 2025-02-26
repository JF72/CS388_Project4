package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

const val MOVIE_EXTRA = "MOVIE_EXTRA"

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val posterImageView = itemView.findViewById<ImageView>(R.id.moviePoster)
        private val titleTextView = itemView.findViewById<TextView>(R.id.movieTitle)

        init {
            itemView.setOnClickListener(this)
        }
        fun bind(movie: Movie) {
            titleTextView.text = movie.title

            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

            Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions().transform(RoundedCorners(30)))
                .into(posterImageView)
        }

        override fun onClick(v: View?) {
            val movie = movies[absoluteAdapterPosition]
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("MOVIE_TITLE", movie.title)
                putExtra("MOVIE_OVERVIEW", movie.overview)
                putExtra("MOVIE_POSTER", movie.posterUrl)
            }
            context.startActivity(intent)
        }
    }
}
