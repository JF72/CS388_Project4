package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"

// Use the API key from BuildConfig
private val MOVIE_API_URL = "https://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.TMDB_API_KEY

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var moviesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up RecyclerView
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView)
        movieAdapter = MovieAdapter(this, movies)
        moviesRecyclerView.adapter = movieAdapter
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch movies from TMDb API
        fetchMovies()
    }

    private fun fetchMovies() {
        val client = AsyncHttpClient()
        client.get(MOVIE_API_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch movies: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched movies: $json")
                try {
                    // Parse JSON response
                    val parsedJson = Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                    }.decodeFromString<MovieResponse>(json.jsonObject.toString())

                    // Save movies to list and refresh RecyclerView
                    parsedJson.results?.let { list ->
                        movies.clear()
                        movies.addAll(list)
                        movieAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}
