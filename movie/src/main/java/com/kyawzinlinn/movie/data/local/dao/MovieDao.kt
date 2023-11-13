package com.kyawzinlinn.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawzinlinn.movie.data.local.entitiy.MovieEntity
import com.kyawzinlinn.movie.utils.MovieType
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies (items : List<MovieEntity>)

    @Query("select * from movie where movieType =:movieType")
    fun getAllMovies(movieType: MovieType) : Flow<List<MovieEntity>>
}