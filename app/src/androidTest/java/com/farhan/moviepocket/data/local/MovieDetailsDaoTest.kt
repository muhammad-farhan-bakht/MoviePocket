package com.farhan.moviepocket.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.farhan.moviepocket.data.local.dao.MovieDetailsDao
import com.farhan.moviepocket.data.local.database.MoviesDatabase
import com.farhan.moviepocket.data.local.entity.MovieDetailsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDetailsDaoTest {

    private lateinit var database: MoviesDatabase
    private lateinit var movieDetailsDao: MovieDetailsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runTest {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        ).allowMainThreadQueries().build()
        movieDetailsDao = database.movieDetailsDao()

        // Insert movies in order to test that results are sorted by id
        movieDetailsDao.insertMovieDetails(getMoveDetailsEntity())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetMoviesEntity() = runTest {
        val movieDetails= movieDetailsDao.getMovieDetailsByMovieId(movieId = 1L)

        Assert.assertEquals(movieDetails?.id, 1L)
        Assert.assertEquals(movieDetails?.id, getMoveDetailsEntity().id)
        Assert.assertEquals(movieDetails?.title, getMoveDetailsEntity().title)
    }

    private fun getMoveDetailsEntity(): MovieDetailsEntity {
        return MovieDetailsEntity(
            id = 1,
            title = "title",
            rating = 3.0,
            backdropPath = "url",
            posterPath = "url",
            tagline = null,
            runtime = null,
            releaseDate = null,
            description = null,
            status = null,
            genres = null
        )
    }
}