package com.daou.deliagent.shared.data

import com.daou.deliagent.shared.entity.ClipArticle
import com.daou.deliagent.shared.di.TimesDatabaseWrapper
import com.daou.deliagent.shared.entity.Article
import com.daou.deliagent.shared.entity.SearchWord
import com.daou.deliagent.shared.network.TimesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TimesRepository : KoinComponent, TimesRepositoryInterface {
    private val api: TimesApi by inject()
    private val timesDatabase: TimesDatabaseWrapper by inject()
    private val dbQuery = timesDatabase.instance.appDatabaseQueries

    override suspend fun searchNews(searchText: String, page: Int): List<Article> {
        val result = kotlin.runCatching { api.searchNews(searchText, page) }
        if (result.isFailure) {
            var message = ""
            result.getOrElse { throwable -> (throwable.message ?: "").also { message = it } }
            throw BaseException(
                code = DEFAULT_ERROR_CODE,
                errorMessage = message
            )
        }
        return result.getOrNull()?.response?.docs ?: listOf()
    }


    override fun getAllClipArticle(): List<ClipArticle> {
        return dbQuery.selectAllClipArticle().executeAsList().map {
            ClipArticle(
                id = it.id,
                linkUrl = it.linkUrl,
                date = it.publishDate,
                title = it.title,
                thumbnailUrl = it.thumbnailUrl
            )
        }
    }

    override fun addClipArticle(article: ClipArticle) {
        dbQuery.insertClipArticle(
            id = null,
            linkUrl = article.linkUrl,
            publishDate = article.date,
            title = article.title,
            thumbnailUrl = article.thumbnailUrl
        )
    }

    override fun removeClipArticle(id: Long) {
        dbQuery.deleteClipArticle(id)
    }

    override fun getRecentSearchKeyword(): List<SearchWord> {
        return dbQuery.selectRecentSearchWord().executeAsList().map {
            SearchWord(
                id = it.id,
                keyword = it.keyword
            )
        }
    }

    override fun addSearchKeyword(keyword: String) {
        dbQuery.insertSearchWord(null, keyword)
    }

    companion object {
        private const val DEFAULT_ERROR_CODE = "9999"
    }
}