package com.farhan.moviepocket.ui.details.state

import com.farhan.moviepocket.data.model.MovieDetails

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Error(val message: String? = null) : MovieDetailsState()
    data class Success(val data: MovieDetails) : MovieDetailsState()
}