package com.farhan.moviepocket.ui.details

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.farhan.moviepocket.data.model.MovieDetails
import com.farhan.moviepocket.data.repository.MovieDetailsRepository
import com.farhan.moviepocket.ui.details.state.MovieDetailsState
import com.farhan.moviepocket.ui.details.viewmodel.MovieDetailsViewModel
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
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDetailsViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
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
    fun `Given MovieDetails are loaded When data source is success Then emit success view state`() = runTest {
        val expectedMovieDetails = getMovieDetails()

        whenever(mockMovieDetailsRepository.getMovieDetailsByMovieId(movieId = 1L)).thenReturn(expectedMovieDetails)
        movieDetailsViewModel = MovieDetailsViewModel(mockMovieDetailsRepository)

        val stateFlowWithLoading = movieDetailsViewModel.viewState.first()
        assertEquals(stateFlowWithLoading, MovieDetailsState.Loading)

        movieDetailsViewModel.getMovieDetails(movieId = 1L)
        val stateFlowWithSuccess = movieDetailsViewModel.viewState.first()
        assertEquals(stateFlowWithSuccess, MovieDetailsState.Success(expectedMovieDetails.first()))
    }

    @Test
    fun `Given MovieDetails are loaded When data source is error Then emit error view state`() =
        runTest {
            whenever(mockMovieDetailsRepository.getMovieDetailsByMovieId(movieId = 1L)).thenThrow(RuntimeException("Error"))

            movieDetailsViewModel = MovieDetailsViewModel(mockMovieDetailsRepository)
            movieDetailsViewModel.getMovieDetails(movieId = 1L)

            val stateFlow = movieDetailsViewModel.viewState.first()
            assertEquals(stateFlow, MovieDetailsState.Error("Error"))
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