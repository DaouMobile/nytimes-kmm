package com.daou.deliagent.shared.network

import com.daou.deliagent.shared.entity.SearchNewsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class TimesApi(private val client: HttpClient) : KoinComponent {
    suspend fun searchNews(searchText: String, page: Int): SearchNewsResponse {
        return client.get(TIMES_SEARCH_URL) {
            parameter("q", searchText)
            parameter("page", page)
            parameter("sort", "newest")
        }
    }

    companion object {
        private const val TIMES_SEARCH_URL =
            "https://api.nytimes.com/svc/search/v2/articlesearch.json"
    }
}