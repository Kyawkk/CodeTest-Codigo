package com.kyawzinlinn.movie.data.repository

import com.kyawzinlinn.movie.data.remote.ApiService
import com.kyawzinlinn.movie.domain.model.MovieDetailResponse
import com.kyawzinlinn.movie.domain.model.PopularMoviesResponse
import com.kyawzinlinn.movie.domain.model.UpcomingMoviesResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : RemoteDataSource {
    override suspend fun getPopularMovies(): PopularMoviesResponse = apiService.getPopularMovies()
    override suspend fun getUpcomingMovies(): UpcomingMoviesResponse = apiService.getUpcomingMovies()
    override suspend fun getMovieDetails(movieId: String): MovieDetailResponse = apiService.getMovieDetails(movieId)
}