package com.kyawzinlinn.movie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kyawzinlinn.movie.data.local.dao.FavoriteDao
import com.kyawzinlinn.movie.data.local.dao.MovieDao
import com.kyawzinlinn.movie.data.local.entitiy.FavoriteMovieEntity
import com.kyawzinlinn.movie.data.local.entitiy.MovieEntity

@Database (entities = [MovieEntity::class,FavoriteMovieEntity::class], version = 4, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun favoriteDao() : FavoriteDao
}