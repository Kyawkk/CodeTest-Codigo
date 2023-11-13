package com.kyawzinlinn.movie.di

import android.content.Context
import androidx.room.Room
import com.kyawzinlinn.movie.data.local.dao.MovieDao
import com.kyawzinlinn.movie.data.local.MovieDatabase
import com.kyawzinlinn.movie.data.local.dao.FavoriteDao
import com.kyawzinlinn.movie.data.repository.MovieRepository
import com.kyawzinlinn.movie.data.repository.MovieRepositoryImpl
import com.kyawzinlinn.movie.data.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) : MovieDatabase {
        return Room.databaseBuilder(
            context = context,
            MovieDatabase::class.java,
            "MovieDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(movieDatabase: MovieDatabase) : MovieDao = movieDatabase.movieDao()

    @Provides
    @Singleton
    fun providesFavoriteDao(movieDatabase: MovieDatabase) : FavoriteDao = movieDatabase.favoriteDao()

    @Provides
    @Singleton
    fun providesMovieRepository(remoteDataSource: RemoteDataSource,movieDao: MovieDao, favoriteDao: FavoriteDao) : MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, movieDao,favoriteDao)
    }
}