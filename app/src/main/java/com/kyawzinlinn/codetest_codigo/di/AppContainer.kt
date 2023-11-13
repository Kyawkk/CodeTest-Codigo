package com.kyawzinlinn.codetest_codigo.di

import android.content.Context
import com.kyawzinlinn.codetest_codigo.data.repository.DataRepository
import com.kyawzinlinn.codetest_codigo.data.repository.DataRepositoryImpl

interface AppContainer {
    val dataRepository: DataRepository
}

class AppDataContainer(context: Context): AppContainer {
    override val dataRepository: DataRepository = DataRepositoryImpl(context)
}