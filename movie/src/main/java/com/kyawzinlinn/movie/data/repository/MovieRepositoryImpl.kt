package com.kyawzinlinn.movie.data.repository

import android.util.Log
import com.kyawzinlinn.movie.data.local.dao.FavoriteDao
import com.kyawzinlinn.movie.data.local.dao.MovieDao
import com.kyawzinlinn.movie.data.local.entitiy.FavoriteMovieEntity
import com.kyawzinlinn.movie.data.local.entitiy.MovieEntity
import com.kyawzinlinn.movie.domain.model.MovieDetailResponse
import com.kyawzinlinn.movie.domain.toMovieEntityList
import com.kyawzinlinn.movie.utils.ExceptionHandler
import com.kyawzinlinn.movie.utils.MovieType
import com.kyawzinlinn.movie.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor (
    private val remoteDataSource: RemoteDataSource,
    private val movieDao: MovieDao,
    private val favoriteDao: FavoriteDao
) : MovieRepository {
    override suspend fun getUpcomingMovies(): Flow<Resource<List<MovieEntity>>> = flow {
        emit(Resource.Loading)

        val favoriteMovieIds = favoriteDao.getAllFavorites(MovieType.Upcoming).first().map { it.movieId }
        val cachedMovies = movieDao.getAllMovies(MovieType.Upcoming).map { movies ->
            movies.map {
                it.copy(favorite = it.id.toString() in favoriteMovieIds)
            }
        }
        emit(Resource.Success(cachedMovies.first()))
        try {
            val upcomingMovies = remoteDataSource.getUpcomingMovies().results.toMovieEntityList(MovieType.Upcoming)
            emit(Resource.Success(upcomingMovies))
            upcomingMovies.map {
                val isFavorite = it.id.toString() in favoriteMovieIds
                it.copy(favorite = isFavorite)
            }
            movieDao.insertAllMovies(upcomingMovies)
        }catch (e: Exception) {
            emit(Resource.Error(ExceptionHandler.handle(e)))

            cachedMovies.collect {
                emit(Resource.Success(it))
            }
        }
    }

    override suspend fun getPopularMovies(): Flow<Resource<List<MovieEntity>>> = flow {
        emit(Resource.Loading)

        val favoriteMovieIds = favoriteDao.getAllFavorites(MovieType.Popular).first().map { it.movieId }
        val cachedMovies = movieDao.getAllMovies(MovieType.Popular).map { movies ->
            movies.map {
                it.copy(favorite = it.id.toString() in favoriteMovieIds)
            }
        }

        emit(Resource.Success(cachedMovies.first()))

        try {
            val popularMovies = remoteDataSource.getUpcomingMovies().results.toMovieEntityList(MovieType.Popular)
            popularMovies.map {
                val isFavorite = it.id.toString() in favoriteMovieIds
                it.copy(favorite = isFavorite)
            }
            emit(Resource.Success(popularMovies))
            movieDao.insertAllMovies(popularMovies)
        }catch (e: Exception) {
            emit(Resource.Error(ExceptionHandler.handle(e)))

            cachedMovies.collect {
                emit(Resource.Success(it))
            }
        }
    }

    override suspend fun getMovieDetails(movieId: String): Flow<Resource<MovieDetailResponse>> = flow {
        try {
            val movieDetails = remoteDataSource.getMovieDetails(movieId)
            emit(Resource.Success(movieDetails))
        }catch (e: Exception) {
            emit(Resource.Error(ExceptionHandler.handle(e)))
        }
    }

    override suspend fun addFavorite(favoriteMovieEntity: FavoriteMovieEntity) = favoriteDao.addFavorite(favoriteMovieEntity)

    override suspend fun removeFavorite(movieId: String, movieType: MovieType) = favoriteDao.deleteFavoriteMovie(movieId,movieType)
}