package com.daou.timesapp.mapper

import com.daou.deliagent.shared.common.Mapper
import com.daou.deliagent.shared.entity.Article
import com.daou.timesapp.ui.home.model.ArticleViewData

class ArticleMapper : Mapper<Article, ArticleViewData> {
    override fun mapFrom(from: Article): ArticleViewData {
        val url = from.multimedia?.firstOrNull()?.url
        val thumbnailUrl = if (url.isNullOrBlank()) "" else BASE_URL + url
        return ArticleViewData(
            title = from.headline?.main ?: "",
            thumbnailUrl = thumbnailUrl,
            linkUrl = from.webUrl ?: "",
            date = from.pubDate ?: ""
        )
    }

    override fun mapAll(from: List<Article>): List<ArticleViewData> {
        return from.map {
            val url = it.multimedia?.firstOrNull()?.url
            val thumbnailUrl = if (url.isNullOrBlank()) "" else BASE_URL + url
            ArticleViewData(
                title = it.headline?.main ?: "",
                thumbnailUrl = thumbnailUrl,
                linkUrl = it.webUrl ?: "",
                date = it.pubDate ?: ""
            )
        }
    }

    companion object {
        private const val BASE_URL = "https://www.nytimes.com/"
    }
}