package com.farhan.moviepocket.di

import com.farhan.moviepocket.data.local.dao.MovieDetailsDao
import com.farhan.moviepocket.data.local.dao.MovieListDao
import com.farhan.moviepocket.data.mapper.MovieDetailsDtoToMovieDetailsEntityMapper
import com.farhan.moviepocket.data.mapper.MovieDetailsEntityToMovieDomainMapper
import com.farhan.moviepocket.data.mapper.MovieDtoToMovieEntityMapper
import com.farhan.moviepocket.data.mapper.MovieEntityToMovieDomainMapper
import com.farhan.moviepocket.data.remote.api.ApiServices
import com.farhan.moviepocket.data.repository.MovieDetailsRepository
import com.farhan.moviepocket.data.repository.MovieDetailsRepositoryImpl
import com.farhan.moviepocket.data.repository.MoviesListRepository
import com.farhan.moviepocket.data.repository.MoviesListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMoviesListRepository(
        apiServices: ApiServices,
        movieListDao: MovieListDao,
        movieDtoToMovieEntityMapper: MovieDtoToMovieEntityMapper,
        movieEntityToMovieDomainMapper: MovieEntityToMovieDomainMapper
    ): MoviesListRepository {
        return MoviesListRepositoryImpl(
            apiServices = apiServices,
            movieListDao = movieListDao,
            movieDtoToMovieEntityMapper = movieDtoToMovieEntityMapper,
            movieEntityToMovieDomainMapper = movieEntityToMovieDomainMapper
        )
    }

    @Singleton
    @Provides
    fun provideMovieDetailsRepository(
        apiServices: ApiServices,
        movieDetailsDao: MovieDetailsDao,
        movieDetailsDtoToMovieDetailsEntityMapper: MovieDetailsDtoToMovieDetailsEntityMapper,
        movieDetailsEntityToMovieDomainMapper: MovieDetailsEntityToMovieDomainMapper
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(
            apiServices = apiServices,
            movieDetailsDao = movieDetailsDao,
            movieDetailsDtoToMovieDetailsEntityMapper = movieDetailsDtoToMovieDetailsEntityMapper,
            movieDetailsEntityToMovieDomainMapper = movieDetailsEntityToMovieDomainMapper
        )
    }
}