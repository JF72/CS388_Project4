package com.codepath.articlesearch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra("MOVIE_TITLE")
        val overview = intent.getStringExtra("MOVIE_OVERVIEW")
        val posterUrl = intent.getStringExtra("MOVIE_POSTER")

        val titleTextView = findViewById<TextView>(R.id.detailTitle)
        val overviewTextView = findViewById<TextView>(R.id.detailOverview)
        val posterImageView = findViewById<ImageView>(R.id.detailPoster)

        titleTextView.text = title
        overviewTextView.text = overview
        Glide.with(this).load(posterUrl).into(posterImageView)
    }
}
