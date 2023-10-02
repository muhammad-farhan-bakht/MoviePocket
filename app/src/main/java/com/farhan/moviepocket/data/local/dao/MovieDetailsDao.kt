package com.farhan.moviepocket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farhan.moviepocket.data.local.entity.MovieDetailsEntity

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(data: MovieDetailsEntity)

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    fun getMovieDetailsByMovieId(movieId: Long) : MovieDetailsEntity?

}