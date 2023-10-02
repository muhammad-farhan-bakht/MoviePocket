package com.farhan.moviepocket.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.farhan.moviepocket.data.local.dao.MovieListDao
import com.farhan.moviepocket.data.local.database.MoviesDatabase
import com.farhan.moviepocket.data.local.entity.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieListDaoTest {

    private lateinit var database: MoviesDatabase
    private lateinit var movieListDao: MovieListDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runTest {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        ).allowMainThreadQueries().build()
        movieListDao = database.movieListDao()

        // Insert movies in order to test that results are sorted by id
        movieListDao.insertMovies(listOf(getMovieEntity(),getMovieEntityWithHighRating()))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetMoviesEntity() = runTest {
        val movieEntityList = movieListDao.getMovies()

        assertEquals(movieEntityList?.size, 1)
        assertEquals(movieEntityList?.get(0),getMovieEntityWithHighRating())
    }

    @Test
    fun testGetMoviesOffline()  = runTest {
        val movieEntityOfflineList = movieListDao.getMoviesOffline() ?: emptyList()

        assertEquals(movieEntityOfflineList.size, 0)
        assertEquals(movieEntityOfflineList.isEmpty(),true)
    }

    private fun getMovieEntity(): MovieEntity {
        return MovieEntity(
            id = 1,
            title = "title",
            rating = 3.0,
            backdropPath = "url"
        )
    }

    private fun getMovieEntityWithHighRating(): MovieEntity {
        return MovieEntity(
            id = 1,
            title = "title",
            rating = 5.0,
            backdropPath = "url"
        )
    }
}