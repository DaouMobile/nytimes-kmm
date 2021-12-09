package com.daou.deliagent.shared.di

import com.daou.deliagent.shared.cache.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(AppDatabase.Schema, get(), "times.db")
        TimesDatabaseWrapper(AppDatabase(driver))
    }
}