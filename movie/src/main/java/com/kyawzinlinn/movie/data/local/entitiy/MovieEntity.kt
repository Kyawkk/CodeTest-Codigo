package com.kyawzinlinn.movie.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawzinlinn.movie.utils.MovieType

@Entity("movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val entityId: Int,
    val movieType: MovieType,
    val favorite: Boolean = false,
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)