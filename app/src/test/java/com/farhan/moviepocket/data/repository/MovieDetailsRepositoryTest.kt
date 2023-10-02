package com.farhan.moviepocket.data.repository

import com.farhan.moviepocket.data.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieDetailsRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val mockMovieDetailsRepository: MovieDetailsRepository = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetMovieDetailsByMovieId() = runTest {
        whenever(mockMovieDetailsRepository.getMovieDetailsByMovieId(movieId = 1)).thenReturn(getMovieDetails())

        val movieDetails = mockMovieDetailsRepository.getMovieDetailsByMovieId(movieId = 1).first()

        Assert.assertEquals(movieDetails.id, mockMovieDetailsRepository.getMovieDetailsByMovieId(movieId = 1).first().id)
    }

    private fun getMovieDetails(): Flow<MovieDetails> = flowOf(
        MovieDetails(
            id = 1,
            title = null,
            rating = null,
            backdropPath = null,
            posterPath = null,
            tagline = null,
            runtime = null,
            releaseDate = null,
            description = null,
            status = null,
            genres = null
        )
    )
}