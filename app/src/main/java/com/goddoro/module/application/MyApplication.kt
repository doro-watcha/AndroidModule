package com.goddoro.module.application

import android.app.Application
import com.goddoro.module.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


/**
 * created By DORO 5/7/21
 */

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        inject()
    }

    private fun inject() {
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.INFO)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    apiModule,
                    repositoryModule
                    //roomModule

                )
            )
        }
    }
}