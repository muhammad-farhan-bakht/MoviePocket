package com.farhan.moviepocket.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDtoToMovieEntityMapperTest {

    private val movieDtoToMovieEntityMapper = MovieDtoToMovieEntityMapper()

    @Test
    fun mapFrom() {
        val movieDto = MapperData.getMovieDto().results.first()
        val movieEntity = movieDtoToMovieEntityMapper.mapFrom(movieDto)
        assertEquals(movieDto.id, movieEntity.id)
    }

    @Test
    fun mapList() {
        val movieDto = MapperData.getMovieDto().results.first()
        val movieEntityList = movieDtoToMovieEntityMapper.mapList(listOf(movieDto))
        assertEquals(movieDto.id, movieEntityList[0].id)
    }
}