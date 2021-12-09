package com.daou.deliagent.shared.di

import com.daou.deliagent.shared.cache.AppDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(AppDatabase.Schema, "times.db")
        TimesDatabaseWrapper(AppDatabase(driver))
    }
}
