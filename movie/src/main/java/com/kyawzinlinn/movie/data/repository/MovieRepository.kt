package com.kyawzinlinn.movie.data.repository

import com.kyawzinlinn.movie.data.local.entitiy.FavoriteMovieEntity
import com.kyawzinlinn.movie.data.local.entitiy.MovieEntity
import com.kyawzinlinn.movie.domain.model.MovieDetailResponse
import com.kyawzinlinn.movie.domain.model.PopularMoviesResponse
import com.kyawzinlinn.movie.domain.model.UpcomingMoviesResponse
import com.kyawzinlinn.movie.utils.MovieType
import com.kyawzinlinn.movie.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies() : Flow<Resource<List<MovieEntity>>>
    suspend fun getUpcomingMovies() : Flow<Resource<List<MovieEntity>>>
    suspend fun getMovieDetails(movieId: String) : Flow<Resource<MovieDetailResponse>>
    suspend fun addFavorite(favoriteMovieEntity: FavoriteMovieEntity)
    suspend fun removeFavorite(movieId: String,movieType: MovieType)
}