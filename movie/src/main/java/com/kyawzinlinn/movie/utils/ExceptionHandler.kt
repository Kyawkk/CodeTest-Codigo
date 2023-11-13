package com.kyawzinlinn.movie.utils

import okio.IOException
import retrofit2.HttpException

object ExceptionHandler {
    fun handle(e: Exception) : String{
        return when (e) {
            is IOException -> "No connection: Check your connection and try again!"
            else -> e.message.toString()
        }
    }
}