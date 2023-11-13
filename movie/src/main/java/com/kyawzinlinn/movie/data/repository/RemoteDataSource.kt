package com.kyawzinlinn.movie.data.repository

import com.kyawzinlinn.movie.domain.model.MovieDetailResponse
import com.kyawzinlinn.movie.domain.model.PopularMoviesResponse
import com.kyawzinlinn.movie.domain.model.UpcomingMoviesResponse

interface RemoteDataSource {
    suspend fun getPopularMovies() : PopularMoviesResponse
    suspend fun getUpcomingMovies() : UpcomingMoviesResponse
    suspend fun getMovieDetails(movieId: String) : MovieDetailResponse
}