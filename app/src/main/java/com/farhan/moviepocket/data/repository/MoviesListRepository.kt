package com.farhan.moviepocket.data.repository

import com.farhan.moviepocket.app.Constants
import com.farhan.moviepocket.data.local.dao.MovieListDao
import com.farhan.moviepocket.data.mapper.MovieDtoToMovieEntityMapper
import com.farhan.moviepocket.data.mapper.MovieEntityToMovieDomainMapper
import com.farhan.moviepocket.data.model.Movie
import com.farhan.moviepocket.data.remote.api.ApiServices
import com.farhan.moviepocket.data.remote.dtos.MovieDto
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

interface MoviesListRepository {
     fun getMovies(isOnline: Boolean, onError: (String) -> Unit): Flow<List<Movie>>
}

class MoviesListRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val movieListDao: MovieListDao,
    private val movieDtoToMovieEntityMapper: MovieDtoToMovieEntityMapper,
    private val movieEntityToMovieDomainMapper: MovieEntityToMovieDomainMapper
) : MoviesListRepository {

    override  fun getMovies(isOnline: Boolean, onError: (String) -> Unit): Flow<List<Movie>> = flow {
        val moviesEntity = if (isOnline) {
            movieListDao.getMovies()
        } else {
            movieListDao.getMoviesOffline()
        }
        if (moviesEntity.isNullOrEmpty()) {
            if (isOnline) {
                apiServices.getMovies()
                    .suspendOnSuccess {
                        insertMovies(data.results)
                        movieListDao.getMovies()?.let {
                            emit(movieEntityToMovieDomainMapper.mapList(it))
                            //emit(movieEntityToMovieDomainMapper.mapList(movieDtoToMovieEntityMapper.mapList(data.results)))
                        } ?: onError(Constants.NO_INTERNET_ERROR_MESSAGE)
                    }
                    .onError {
                        Timber.e(message())
                    }
                    .onException {
                        Timber.e(message())
                    }
            } else {
                onError(Constants.NO_INTERNET_ERROR_MESSAGE)
            }
        } else {
            emit(movieEntityToMovieDomainMapper.mapList(moviesEntity))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun insertMovies(data: List<MovieDto.Result>) {
        withContext(Dispatchers.IO) {
            val moviesEntity = movieDtoToMovieEntityMapper.mapList(data)
            movieListDao.insertMovies(moviesEntity)
        }
    }
}