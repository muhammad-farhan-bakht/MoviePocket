package com.farhan.moviepocket.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farhan.moviepocket.data.local.dao.MovieDetailsDao
import com.farhan.moviepocket.data.local.dao.MovieListDao
import com.farhan.moviepocket.data.local.entity.MovieDetailsEntity
import com.farhan.moviepocket.data.local.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieDetailsEntity::class
    ],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME: String = "movie_db"
    }

    abstract fun movieListDao(): MovieListDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}