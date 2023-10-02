package com.farhan.moviepocket.data.mapper

import com.farhan.moviepocket.data.local.entity.MovieEntity
import com.farhan.moviepocket.data.remote.dtos.MovieDto
import javax.inject.Inject

class MovieDtoToMovieEntityMapper @Inject constructor() : DataMapper<MovieDto.Result,MovieEntity> {

    override fun mapFrom(value: MovieDto.Result): MovieEntity {
        return MovieEntity(
            id = value.id,
            title = value.title,
            rating = value.rating,
            backdropPath = value.backdropPath
        )
    }

    override fun mapList(value: List<MovieDto.Result>): List<MovieEntity> {
        return value.map { mapFrom(it) }
    }
}