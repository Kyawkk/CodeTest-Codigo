package com.kyawzinlinn.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawzinlinn.movie.data.local.entitiy.FavoriteMovieEntity
import com.kyawzinlinn.movie.utils.MovieType
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite (favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun removeFavorite(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("delete from favorites where movieId=:movieId and movieType=:movieType")
    fun deleteFavoriteMovie(movieId: String, movieType: MovieType)

    @Query("select * from favorites where movieType=:movieType")
    fun getAllFavorites(movieType: MovieType) : Flow<List<FavoriteMovieEntity>>
}