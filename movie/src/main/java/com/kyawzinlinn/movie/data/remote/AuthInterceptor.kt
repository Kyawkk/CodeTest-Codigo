package com.kyawzinlinn.movie.data.remote

import com.kyawzinlinn.movie.utils.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("Authorization","Bearer $API_KEY")
            .build()
        return chain.proceed(newRequest)
    }

}