package com.kyawzinlinn.movie.domain

import com.kyawzinlinn.movie.data.local.entitiy.FavoriteMovieEntity
import com.kyawzinlinn.movie.data.local.entitiy.MovieEntity
import com.kyawzinlinn.movie.domain.model.MovieDto
import com.kyawzinlinn.movie.utils.MovieType

fun List<MovieDto>.toMovieEntityList(movieType: MovieType): List<MovieEntity> {
    return map {
        MovieEntity(
            entityId = 0,
            movieType = movieType,
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            id = it.id,
            original_language = it.original_language,
            original_title = it.original_title,
            overview = it.overview,
            popularity = it.popularity,
            poster_path = it.poster_path,
            release_date = it.release_date,
            title = it.title,
            video = it.video,
            vote_average = it.vote_average,
            vote_count = it.vote_count
        )
    }
}

fun MovieEntity.toFavoriteMovieEntity(movieType: MovieType): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = 0,
        movieId = this.id.toString(),
        movieType = movieType
    )
}