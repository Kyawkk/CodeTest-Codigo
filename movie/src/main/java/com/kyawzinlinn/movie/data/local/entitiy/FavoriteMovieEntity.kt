package com.kyawzinlinn.movie.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawzinlinn.movie.utils.MovieType

@Entity("favorites")
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: String,
    val movieType: MovieType
)
