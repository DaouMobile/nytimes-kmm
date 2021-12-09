package com.daou.deliagent.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClipArticle(
    @SerialName("id")
    var id: Long?,
    @SerialName("linkUrl")
    var linkUrl: String,
    @SerialName("date")
    var date: String,
    @SerialName("title")
    var title: String,
    @SerialName("thumbnailUrl")
    var thumbnailUrl: String
)
