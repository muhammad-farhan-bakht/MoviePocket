package com.farhan.moviepocket.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result> = arrayListOf(),
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
    data class Result(
        @SerializedName("id")
        val id: Long,
        @SerializedName("title")
        val title: String,
        @SerializedName("vote_average")
        val rating: Double,
        @SerializedName("backdrop_path")
        val backdropPath: String
    )
}