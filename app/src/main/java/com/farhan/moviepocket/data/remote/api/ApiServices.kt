package com.farhan.moviepocket.data.remote.api

import com.farhan.moviepocket.app.Constants
import com.farhan.moviepocket.data.remote.dtos.MovieDetailsDto
import com.farhan.moviepocket.data.remote.dtos.MovieDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("3/movie/popular")
    suspend fun getMovies(
        @Query("api_key")
        api_key: String = Constants.THE_MOVIE_DB_API_KEY,
    ):  ApiResponse<MovieDto>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id")
        id: Long,
        @Query("api_key")
        api_key: String = Constants.THE_MOVIE_DB_API_KEY
    ): ApiResponse<MovieDetailsDto>
}