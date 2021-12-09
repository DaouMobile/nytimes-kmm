package com.daou.deliagent.shared.data

import com.daou.deliagent.shared.entity.ClipArticle
import com.daou.deliagent.shared.entity.Article
import com.daou.deliagent.shared.entity.SearchWord

interface TimesRepositoryInterface {
    suspend fun searchNews(searchText: String, page: Int = 0): List<Article>
    fun getAllClipArticle(): List<ClipArticle>
    fun addClipArticle(article: ClipArticle)
    fun removeClipArticle(id: Long)
    fun getRecentSearchKeyword(): List<SearchWord>
    fun addSearchKeyword(keyword: String)
}