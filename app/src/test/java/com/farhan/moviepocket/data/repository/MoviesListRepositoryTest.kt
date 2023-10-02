package com.farhan.moviepocket.data.repository

import com.farhan.moviepocket.data.model.Movie
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class MoviesListRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetMovies() = runTest {
        val mockMoviesListRepository = mock<MoviesListRepository> {
            on { getMovies(any(), any()) } doReturn getMoviesFlow()
        }

        val movieList = mockMoviesListRepository.getMovies(isOnline = true, onError = {}).first()

        assertEquals(movieList.first(), getMoviesFlow().first().first())
        assertEquals(movieList.size, getMoviesFlow().first().size)
    }

    private fun getMoviesFlow(): Flow<List<Movie>> = flowOf(
        listOf(
            Movie(
                id = 1,
                title = "title",
                rating = 3.0,
                backdropPath = "url"
            )
        )
    )
}