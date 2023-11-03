package com.esteban.lopez.pruebatodoservy

import android.app.Application
import com.esteban.lopez.pruebatodoservy.di.datasourceModule
import com.esteban.lopez.pruebatodoservy.di.roomModule
import com.esteban.lopez.pruebatodoservy.di.repositoryModule
import com.esteban.lopez.pruebatodoservy.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(repositoryModule, viewModelModule, datasourceModule,roomModule))
        }
    }
}