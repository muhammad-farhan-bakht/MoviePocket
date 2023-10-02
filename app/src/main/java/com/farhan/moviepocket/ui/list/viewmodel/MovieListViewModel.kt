package com.farhan.moviepocket.ui.list.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhan.moviepocket.data.repository.MoviesListRepository
import com.farhan.moviepocket.ui.list.state.MovieListViewState
import com.farhan.moviepocket.utils.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MoviesListRepository,
    // Ignore Warning: If using Hilt, then we can avoid this memory leak by using the @ApplicationContext annotation.
    // The @ApplicationContext annotation tells Hilt to inject a weak reference to the application context into the MovieListViewModel class.
    // This means that the ViewModel will not hold a strong reference to the application context,
    // and it will be more likely to be garbage collected when it is no longer needed.
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _viewState = MutableStateFlow<MovieListViewState>(MovieListViewState.Loading)
    val viewState: StateFlow<MovieListViewState> = _viewState

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            runCatching {
                repository.getMovies(isOnline = context.hasInternetConnection(),
                    onError = {
                        _viewState.value = MovieListViewState.Error(it)
                    })
            }.onFailure {
                _viewState.value = MovieListViewState.Error(it.message)
            }.onSuccess { moviesFlow ->
                moviesFlow.collect {
                    _viewState.value = MovieListViewState.Success(it)
                }
            }
        }
    }
}