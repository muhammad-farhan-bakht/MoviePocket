package com.farhan.moviepocket.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("vote_average")
    val rating: Double? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("tagline")
    val tagline: String? = null,
    @SerializedName("runtime")
    val runtime: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("overview")
    val description: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("genres")
    val genres: List<Genre>? = arrayListOf()
) {
    data class Genre(
        @SerializedName("name")
        val name: String? = null
    )
}