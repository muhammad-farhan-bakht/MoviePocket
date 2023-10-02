package com.farhan.moviepocket.ui.list.state

import com.farhan.moviepocket.data.model.Movie

sealed class MovieListViewState {
    object Loading : MovieListViewState()
    data class Error(val message: String? = null) : MovieListViewState()
    data class Success(val movies: List<Movie>) : MovieListViewState()
}