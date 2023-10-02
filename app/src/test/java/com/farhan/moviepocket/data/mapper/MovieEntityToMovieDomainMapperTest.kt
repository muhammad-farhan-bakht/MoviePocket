package com.farhan.moviepocket.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieEntityToMovieDomainMapperTest {

    private val movieEntityToMovieDomainMapper = MovieEntityToMovieDomainMapper()

    @Test
    fun mapFrom() {
        val movieEntity = MapperData.getMovieEntity()
        val movie = movieEntityToMovieDomainMapper.mapFrom(movieEntity)
        assertEquals(movie.id, movieEntity.id)
    }

    @Test
    fun mapList() {
        val movieEntity = MapperData.getMovieEntity()
        val movieList = movieEntityToMovieDomainMapper.mapList(listOf(movieEntity))
        assertEquals(movieEntity.id, movieList[0].id)
    }
}