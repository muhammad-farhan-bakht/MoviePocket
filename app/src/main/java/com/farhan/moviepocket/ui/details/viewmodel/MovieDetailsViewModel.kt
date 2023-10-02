package com.farhan.moviepocket.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhan.moviepocket.data.repository.MovieDetailsRepository
import com.farhan.moviepocket.ui.details.state.MovieDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val viewState: StateFlow<MovieDetailsState> = _viewState

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            runCatching {
                repository.getMovieDetailsByMovieId(movieId)
            }.onFailure {
                _viewState.value = MovieDetailsState.Error(it.message)
            }.onSuccess { movieDetailsFlow ->
                movieDetailsFlow.collect {
                    _viewState.value = MovieDetailsState.Success(it)
                }
            }
        }
    }
}