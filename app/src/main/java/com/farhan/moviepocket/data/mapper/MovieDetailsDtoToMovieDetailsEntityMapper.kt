package com.farhan.moviepocket.data.mapper

import com.farhan.moviepocket.data.local.entity.MovieDetailsEntity
import com.farhan.moviepocket.data.remote.dtos.MovieDetailsDto
import javax.inject.Inject

class MovieDetailsDtoToMovieDetailsEntityMapper @Inject constructor() : DataMapper<MovieDetailsDto, MovieDetailsEntity> {

    override fun mapFrom(value: MovieDetailsDto): MovieDetailsEntity {
        return MovieDetailsEntity(
            id = value.id,
            title = value.title,
            rating = value.rating,
            backdropPath = value.backdropPath,
            posterPath = value.posterPath,
            tagline = value.tagline,
            runtime = value.runtime,
            releaseDate = value.releaseDate,
            description = value.description,
            status = value.status,
            genres = value.genres?.joinToString(
                truncated = "",
                separator = ", ",
            ) {it.name ?: "N/A"},
        )
    }

    override fun mapList(value: List<MovieDetailsDto>): List<MovieDetailsEntity> {
        return value.map { mapFrom(it) }
    }
}