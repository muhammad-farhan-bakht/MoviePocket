package com.farhan.moviepocket.data.repository

import com.farhan.moviepocket.data.local.dao.MovieDetailsDao
import com.farhan.moviepocket.data.mapper.MovieDetailsDtoToMovieDetailsEntityMapper
import com.farhan.moviepocket.data.mapper.MovieDetailsEntityToMovieDomainMapper
import com.farhan.moviepocket.data.model.MovieDetails
import com.farhan.moviepocket.data.remote.api.ApiServices
import com.farhan.moviepocket.data.remote.dtos.MovieDetailsDto
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

interface MovieDetailsRepository {
    suspend fun getMovieDetailsByMovieId(movieId: Long): Flow<MovieDetails>
}

class MovieDetailsRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val movieDetailsDao: MovieDetailsDao,
    private val movieDetailsDtoToMovieDetailsEntityMapper: MovieDetailsDtoToMovieDetailsEntityMapper,
    private val movieDetailsEntityToMovieDomainMapper: MovieDetailsEntityToMovieDomainMapper
) : MovieDetailsRepository {

    override suspend fun getMovieDetailsByMovieId(movieId: Long): Flow<MovieDetails> = flow {
        val moveDetails = movieDetailsDao.getMovieDetailsByMovieId(movieId)
        if (moveDetails == null) {
            apiServices.getMovieById(id = movieId)
                .suspendOnSuccess {
                    insertMovieDetails(data)
                    emit(movieDetailsEntityToMovieDomainMapper.mapFrom(movieDetailsDtoToMovieDetailsEntityMapper.mapFrom(data)))
                }
                .onError {
                    Timber.e(message())
                }
                .onException {
                    Timber.e(message())
                }
        } else {
            emit(movieDetailsEntityToMovieDomainMapper.mapFrom(moveDetails))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun insertMovieDetails(data: MovieDetailsDto) {
        withContext(Dispatchers.IO) {
            val moviesEntity = movieDetailsDtoToMovieDetailsEntityMapper.mapFrom(data)
            movieDetailsDao.insertMovieDetails(moviesEntity)
        }
    }
}