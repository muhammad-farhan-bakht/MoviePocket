package com.farhan.moviepocket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farhan.moviepocket.data.local.entity.MovieEntity

@Dao
interface MovieListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(data: List<MovieEntity>)

    @Query("SELECT * FROM movie ORDER BY rating DESC")
    suspend fun getMovies() : List<MovieEntity>?

    @Query("SELECT * FROM movie WHERE EXISTS (SELECT 1 FROM movie_details WHERE movie_details.id = movie.id)")
    suspend fun getMoviesOffline() :List<MovieEntity>?
}