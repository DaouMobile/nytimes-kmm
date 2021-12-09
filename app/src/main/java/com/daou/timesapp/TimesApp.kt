package com.daou.timesapp

import android.app.Application
import com.daou.deliagent.shared.di.initKoin
import com.daou.timesapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class TimesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin() {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@TimesApp)
            modules(appModules)
        }
    }
}