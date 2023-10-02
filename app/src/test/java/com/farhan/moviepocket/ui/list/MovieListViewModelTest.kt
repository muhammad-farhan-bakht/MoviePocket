package com.farhan.moviepocket.ui.list

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.farhan.moviepocket.data.model.Movie
import com.farhan.moviepocket.data.repository.MoviesListRepository
import com.farhan.moviepocket.ui.list.state.MovieListViewState
import com.farhan.moviepocket.ui.list.viewmodel.MovieListViewModel
import com.farhan.moviepocket.utils.hasInternetConnection
import io.mockk.every
import io.mockk.mockkStatic
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
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var movieListViewModel: MovieListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        mockkStatic("com.farhan.moviepocket.utils.ExtensionsKt")
        every {
            any<Context>().hasInternetConnection()
        } returns true
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given movies are loaded When data source is success Then emit success view state`() =
        runTest {

            val expectedMovies = getMoviesFlow()

            val mockMoviesListRepository = mock<MoviesListRepository> {
                on { getMovies(any(),any()) } doReturn expectedMovies
            }

            movieListViewModel = MovieListViewModel(
                mockMoviesListRepository,
                ApplicationProvider.getApplicationContext()
            )

            val stateFlow = movieListViewModel.viewState.first()

            assertEquals(stateFlow, MovieListViewState.Success(expectedMovies.first()))
        }

    @Test
    fun `Given movies are loaded When data source is error Then emit error view state`() =
        runTest {
            val mockMoviesListRepository = mock<MoviesListRepository> {
                on { getMovies(any(),any()) } doThrow RuntimeException("Error")
            }

            movieListViewModel = MovieListViewModel(
                mockMoviesListRepository,
                ApplicationProvider.getApplicationContext()
            )

            val stateFlow = movieListViewModel.viewState.first()

            assertEquals(stateFlow, MovieListViewState.Error("Error"))
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