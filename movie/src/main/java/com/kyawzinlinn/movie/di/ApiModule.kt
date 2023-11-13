package com.kyawzinlinn.movie.di

import com.kyawzinlinn.movie.data.remote.ApiService
import com.kyawzinlinn.movie.data.remote.AuthInterceptor
import com.kyawzinlinn.movie.data.repository.RemoteDataSource
import com.kyawzinlinn.movie.data.repository.RemoteDataSourceImpl
import com.kyawzinlinn.movie.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesApi() : ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        val retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)

        return retrofit
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }
}