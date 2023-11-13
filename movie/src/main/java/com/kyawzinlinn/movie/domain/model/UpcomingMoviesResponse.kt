package com.kyawzinlinn.movie.domain.model

data class UpcomingMoviesResponse(
    val dates: Dates,
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)