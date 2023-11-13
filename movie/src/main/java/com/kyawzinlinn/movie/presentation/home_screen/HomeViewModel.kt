package com.kyawzinlinn.movie.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.movie.data.local.entitiy.FavoriteMovieEntity
import com.kyawzinlinn.movie.data.local.entitiy.MovieEntity
import com.kyawzinlinn.movie.domain.model.MovieDetailResponse
import com.kyawzinlinn.movie.data.repository.MovieRepository
import com.kyawzinlinn.movie.utils.MovieType
import com.kyawzinlinn.movie.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    popularMovies = movieRepository.getPopularMovies(),
                    upcomingMovies = movieRepository.getUpcomingMovies()
                )
            }
        }
    }

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    movieDetails = movieRepository.getMovieDetails(movieId)
                )
            }
        }
    }

    fun toggleFavorite(isFavorite: Boolean, favoriteMovieEntity: FavoriteMovieEntity, movieType: MovieType) {
        viewModelScope.launch (Dispatchers.IO) {
            if (isFavorite)movieRepository.addFavorite(favoriteMovieEntity)
            else movieRepository.removeFavorite(favoriteMovieEntity.movieId,movieType)
        }
    }

}

data class HomeUiState(
    val popularMovies : Flow<Resource<List<MovieEntity>>> = emptyFlow(),
    val upcomingMovies : Flow<Resource<List<MovieEntity>>> = emptyFlow(),
    val movieDetails : Flow<Resource<MovieDetailResponse>> = emptyFlow()
)