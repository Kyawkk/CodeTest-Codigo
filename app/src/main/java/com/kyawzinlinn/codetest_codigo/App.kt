package com.kyawzinlinn.codetest_codigo

import android.app.Application
import com.kyawzinlinn.codetest_codigo.di.AppContainer
import com.kyawzinlinn.codetest_codigo.di.AppDataContainer

class App : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}