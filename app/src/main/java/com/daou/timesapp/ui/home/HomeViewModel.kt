package com.daou.timesapp.ui.home

import com.daou.deliagent.shared.entity.ClipArticle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daou.deliagent.shared.data.BaseException
import com.daou.deliagent.shared.data.TimesRepositoryInterface
import com.daou.timesapp.R
import com.daou.timesapp.mapper.ArticleMapper
import com.daou.timesapp.ui.common.BaseViewModel
import com.daou.timesapp.ui.common.SingleLiveEvent
import com.daou.timesapp.ui.home.model.ArticleViewData

class HomeViewModel(
    private val repository: TimesRepositoryInterface,
    private val articleMapper: ArticleMapper
) : BaseViewModel() {
    val keyword: MutableLiveData<String> = MutableLiveData("")
    val isSearchMode: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _searchList: MutableLiveData<List<ArticleViewData>> = MutableLiveData()
    val searchList: LiveData<List<ArticleViewData>> = _searchList
    private val _keywordList: MutableLiveData<List<String>> = MutableLiveData()
    val keywordList: LiveData<List<String>> = _keywordList
    private val _clickSearchItem = SingleLiveEvent<String>()
    val clickSearchItem: LiveData<String> = _clickSearchItem
    private val _clearEditFocus = SingleLiveEvent<Any>()
    val clearEditFocus: LiveData<Any> = _clearEditFocus
    private var currentPage = 0

    init {
        onClickSearch()
    }

    fun setSearchMode(isFocus: Boolean) {
        if (isFocus) {
            getSearchKeywords()
            isSearchMode.value = true
        } else isSearchMode.value = false
    }

    fun onClickHistoryKeyword(word: String) {
        keyword.value = word
    }

    fun onClickSearch() {
        _clearEditFocus.call()
        launch {
            showProgress()
            val word = keyword.value ?: ""
            currentPage = 0
            _searchList.value = listOf()
            searchNewKeyword(word)
            saveKeyWord(word)
            hideProgress()
        }
    }

    fun onClickItem(item: ArticleViewData) {
        _clickSearchItem.value = item.linkUrl
    }

    fun onClickClipItem(item: ArticleViewData) {
        launch {
            showProgress()
            try {
                repository.addClipArticle(
                    ClipArticle(
                        id = null,
                        linkUrl = item.linkUrl,
                        date = item.date,
                        title = item.title,
                        thumbnailUrl = item.thumbnailUrl
                    )
                )
                hideProgress()
                showToast(R.string.msg_add_success)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Fail to add clip article. title: ${item.title}")
                hideProgress()
                showToast(R.string.msg_add_fail)
            }
        }
    }

    fun searchMore() {
        launch {
            try {
                val result = repository.searchNews(
                    searchText = keyword.value ?: "",
                    page = ++currentPage
                )
                if (result.isEmpty()) {
                    showToast(R.string.msg_not_more)
                } else {
                    val newList = searchList.value?.toMutableList() ?: mutableListOf()
                    newList.addAll(articleMapper.mapAll(result))
                    _searchList.value = newList
                }
            } catch (e: BaseException) {
                Log.e("getImageList", "code: ${e.code}, message: ${e.errorMessage}")
                showToast(R.string.msg_error)
            }
        }
    }

    private fun getSearchKeywords() {
        launch {
            try {
                val result = repository.getRecentSearchKeyword()
                _keywordList.value = result.map { it.keyword }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Fail to get search keyword list. e: ${e.message}")
            }
        }
    }

    private suspend fun searchNewKeyword(keyword: String) {
        try {
            val result = repository.searchNews(
                searchText = keyword,
                page = 0
            )
            _searchList.value = articleMapper.mapAll(result)
        } catch (e: BaseException) {
            Log.e("getImageList", "code: ${e.code}, message: ${e.errorMessage}")
            showToast(R.string.msg_error)
            _searchList.value = listOf()
        }
    }

    private fun saveKeyWord(keyword: String) {
        if (keyword.isBlank()) return
        try {
            repository.addSearchKeyword(keyword)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Fail to add keyword: $keyword")
        }
    }
}