package com.daou.deliagent.shared.di

import TimesApp.shared.BuildConfig
import com.daou.deliagent.shared.data.TimesRepository
import com.daou.deliagent.shared.data.TimesRepositoryInterface
import com.daou.deliagent.shared.network.TimesApi
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(), platformModule())
    }

// called by iOS etc
fun initKoin() = initKoin {}

fun commonModule() = module {
    single { createJson() }
    single { createHttpClient(get()) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single<TimesRepositoryInterface> { TimesRepository() }
    single { TimesApi(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(json: Json) = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }
    defaultRequest {
        parameter("api-key", BuildConfig.API_KEY)
    }
}