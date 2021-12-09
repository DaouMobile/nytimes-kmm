package com.daou.timesapp.mapper

import com.daou.deliagent.shared.common.Mapper
import com.daou.deliagent.shared.entity.ClipArticle
import com.daou.timesapp.ui.clip.model.ClipArticleViewData

class ClipArticleMapper : Mapper<ClipArticle, ClipArticleViewData> {
    override fun mapFrom(from: ClipArticle): ClipArticleViewData {
        return ClipArticleViewData(
            id = from.id,
            title = from.title,
            date = from.date,
            linkUrl = from.linkUrl,
            thumbnailUrl = from.thumbnailUrl
        )
    }

    override fun mapAll(from: List<ClipArticle>): List<ClipArticleViewData> {
        return from.map {
            ClipArticleViewData(
                id = it.id,
                title = it.title,
                date = it.date,
                linkUrl = it.linkUrl,
                thumbnailUrl = it.thumbnailUrl
            )
        }
    }
}