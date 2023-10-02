package com.farhan.moviepocket.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailsEntityToMovieDomainMapperTest {

    private val movieDetailsEntityToMovieDomainMapper = MovieDetailsEntityToMovieDomainMapper()

    @Test
    fun mapFrom() {
        val movieDetailsEntity = MapperData.getMoveDetailsEntity()
        val movieDetails = movieDetailsEntityToMovieDomainMapper.mapFrom(movieDetailsEntity)
        assertEquals(movieDetailsEntity.id, movieDetails.id)
    }

    @Test
    fun mapList() {
        val movieDetailsEntity = MapperData.getMoveDetailsEntity()
        val movieDetailsList = movieDetailsEntityToMovieDomainMapper.mapList(listOf(movieDetailsEntity))
        assertEquals(movieDetailsEntity.id, movieDetailsList[0].id)
    }
}