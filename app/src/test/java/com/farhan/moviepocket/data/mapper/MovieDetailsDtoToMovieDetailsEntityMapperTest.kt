package com.farhan.moviepocket.data.mapper

import com.farhan.moviepocket.data.mapper.MapperData.getMoveDetailsDto
import org.junit.Test
import org.junit.Assert.assertEquals

class MovieDetailsDtoToMovieDetailsEntityMapperTest {

    private val movieDetailsDtoToMovieDetailsEntityMapper = MovieDetailsDtoToMovieDetailsEntityMapper()

    @Test
    fun mapFrom() {
        val movieDetailsDto = getMoveDetailsDto()
        val movieDetailsEntity = movieDetailsDtoToMovieDetailsEntityMapper.mapFrom(movieDetailsDto)
        assertEquals(movieDetailsDto.id,movieDetailsEntity.id)
    }

    @Test
    fun mapList() {
        val movieDetailsDto = getMoveDetailsDto()
        val movieDetailsEntityList = movieDetailsDtoToMovieDetailsEntityMapper.mapList(listOf(movieDetailsDto))
        assertEquals(movieDetailsDto.id, movieDetailsEntityList[0].id)
    }
}