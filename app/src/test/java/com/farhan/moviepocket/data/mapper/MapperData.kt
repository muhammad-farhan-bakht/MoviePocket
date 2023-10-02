package com.farhan.moviepocket.data.mapper

import com.farhan.moviepocket.data.local.entity.MovieDetailsEntity
import com.farhan.moviepocket.data.local.entity.MovieEntity
import com.farhan.moviepocket.data.model.Movie
import com.farhan.moviepocket.data.model.MovieDetails
import com.farhan.moviepocket.data.remote.dtos.MovieDetailsDto
import com.farhan.moviepocket.data.remote.dtos.MovieDto

object MapperData {

    fun getMovieEntity(): MovieEntity {
        return MovieEntity(
            id = 1,
            title = "title",
            rating = 3.0,
            backdropPath = "url"
        )
    }

    fun getMovieDto(): MovieDto {
        return MovieDto(
            page = 0,
            results = listOf(
                MovieDto.Result(
                    id = 1,
                    title = "title",
                    rating = 3.0,
                    backdropPath = "url"
                )
            ),
            totalPages = 0,
            totalResults = 0
        )
    }

    fun getMoveDetailsEntity(): MovieDetailsEntity {
        return MovieDetailsEntity(
            id = 1,
            title = "title",
            rating = 3.0,
            backdropPath = "url",
            posterPath = "url",
            tagline = null,
            runtime = null,
            releaseDate = null,
            description = null,
            status = null,
            genres = null
        )
    }

    fun getMoveDetailsDto(): MovieDetailsDto {
        return MovieDetailsDto(
            id = 1,
            title = "title",
            rating = 3.0,
            backdropPath = "url",
            posterPath = "url",
            tagline = null,
            runtime = null,
            releaseDate = null,
            description = null,
            status = null,
            genres = null
        )
    }
}