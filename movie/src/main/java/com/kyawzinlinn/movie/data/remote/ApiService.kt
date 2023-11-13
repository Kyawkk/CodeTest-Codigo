package com.kyawzinlinn.movie.data.remote

import com.kyawzinlinn.movie.domain.model.MovieDetailResponse
import com.kyawzinlinn.movie.domain.model.PopularMoviesResponse
import com.kyawzinlinn.movie.domain.model.UpcomingMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("upcoming")
    suspend fun getUpcomingMovies() : UpcomingMoviesResponse

    @GET("popular")
    suspend fun getPopularMovies() : PopularMoviesResponse

    @GET("{movieId}")
    suspend fun getMovieDetails(@Path("movieId")movieId: String): MovieDetailResponse
}