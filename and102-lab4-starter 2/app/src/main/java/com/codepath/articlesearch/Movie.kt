package com.codepath.articlesearch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("results") val results: List<Movie>?
)

@Serializable
data class Movie(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String?
) {
    val posterUrl get() = "https://image.tmdb.org/t/p/w500/$posterPath"
}
