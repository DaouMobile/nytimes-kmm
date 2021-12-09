package com.daou.timesapp.ui.clip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daou.deliagent.shared.data.BaseException
import com.daou.deliagent.shared.data.TimesRepositoryInterface
import com.daou.timesapp.R
import com.daou.timesapp.mapper.ClipArticleMapper
import com.daou.timesapp.ui.clip.model.ClipArticleViewData
import com.daou.timesapp.ui.common.BaseViewModel
import com.daou.timesapp.ui.common.SingleLiveEvent

class ClipViewModel(
    private val repository: TimesRepositoryInterface,
    private val clipArticleMapper: ClipArticleMapper
) : BaseViewModel() {
    private val _articleList: MutableLiveData<List<ClipArticleViewData>> = MutableLiveData()
    val articleList: LiveData<List<ClipArticleViewData>> = _articleList
    private val _clickSearchItem = SingleLiveEvent<String>()
    val clickSearchItem: LiveData<String> = _clickSearchItem

    fun onClickItem(item: ClipArticleViewData) {
        _clickSearchItem.value = item.linkUrl
    }

    fun onClickUnClipItem(item: ClipArticleViewData) {
        launch {
            showProgress()
            try {
                item.id?.let { repository.removeClipArticle(it) }
                hideProgress()
                showToast(R.string.msg_remove_success)
                getClippedArticles()
            } catch (e: Exception) {
                Log.e("ClipViewModel", "Fail to remove item id: ${item.id}")
                hideProgress()
                showToast(R.string.msg_remove_fail)
            }
        }
    }

    fun getClippedArticles() {
        launch {
            showProgress()
            try {
                val result = repository.getAllClipArticle()
                _articleList.value = clipArticleMapper.mapAll(result)
            } catch (e: BaseException) {
                Log.e("getMoreArticles", "code: ${e.code}, message: ${e.errorMessage}")
                showToast(R.string.msg_error)
            }
            hideProgress()
        }
    }
}