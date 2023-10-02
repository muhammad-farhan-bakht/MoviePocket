package com.farhan.moviepocket.data.mapper

import com.farhan.moviepocket.data.local.entity.MovieEntity
import com.farhan.moviepocket.data.model.Movie
import javax.inject.Inject

class MovieEntityToMovieDomainMapper @Inject constructor() : DataMapper<MovieEntity, Movie> {

    override fun mapFrom(value: MovieEntity): Movie {
        return Movie(
            id = value.id,
            title = value.title,
            rating = value.rating,
            backdropPath = value.backdropPath
        )
    }

    override fun mapList(value: List<MovieEntity>): List<Movie> {
        return value.map { mapFrom(it) }
    }
}