package com.farhan.moviepocket.data.model

data class MovieDetails(
    val id: Long,
    val title: String? = null,
    val rating: Double? = null,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val tagline: String? = null,
    val runtime: String? = null,
    val releaseDate: String? = null,
    val description: String? = null,
    val status: String? = null,
    val genres: String? = null
)