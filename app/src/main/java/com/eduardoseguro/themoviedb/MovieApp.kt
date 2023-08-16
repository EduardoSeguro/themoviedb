package com.eduardoseguro.themoviedb

import android.app.Application
import com.eduardoseguro.themoviedb.di.movieModule
import com.eduardoseguro.themoviedb.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MovieApp)
            modules(movieModule, networkModule)
        }
    }
}