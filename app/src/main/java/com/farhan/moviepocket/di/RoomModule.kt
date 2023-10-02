package com.farhan.moviepocket.di

import android.content.Context
import androidx.room.Room
import com.farhan.moviepocket.data.local.dao.MovieDetailsDao
import com.farhan.moviepocket.data.local.dao.MovieListDao
import com.farhan.moviepocket.data.local.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): MoviesDatabase {
        return Room
            .databaseBuilder(
                context,
                MoviesDatabase::class.java,
                MoviesDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieListDao(moviesDatabase: MoviesDatabase): MovieListDao {
        return moviesDatabase.movieListDao()
    }

    @Singleton
    @Provides
    fun provideMovieDetailsDao(moviesDatabase: MoviesDatabase): MovieDetailsDao {
        return moviesDatabase.movieDetailsDao()
    }
}