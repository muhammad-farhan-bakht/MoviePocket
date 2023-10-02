package com.farhan.moviepocket.data.mapper

import com.farhan.moviepocket.data.local.entity.MovieDetailsEntity
import com.farhan.moviepocket.data.model.MovieDetails
import javax.inject.Inject

class MovieDetailsEntityToMovieDomainMapper @Inject constructor() : DataMapper<MovieDetailsEntity, MovieDetails> {

    override fun mapFrom(value: MovieDetailsEntity): MovieDetails {
        return MovieDetails(
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
            genres = value.genres
        )
    }

    override fun mapList(value: List<MovieDetailsEntity>): List<MovieDetails> {
        return value.map { mapFrom(it) }
    }
}