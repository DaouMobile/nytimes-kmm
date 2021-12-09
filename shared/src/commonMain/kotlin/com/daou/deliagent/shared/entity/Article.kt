package com.daou.deliagent.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchNewsResponse(
    @SerialName("response")
    val response: BaseResponse?
)

@Serializable
data class BaseResponse(
    @SerialName("docs")
    val docs: List<Article>?
)

@Serializable
data class Article(
    @SerialName("web_url")
    val webUrl: String?,
    @SerialName("pub_date")
    val pubDate: String?,
    @SerialName("headline")
    val headline: HeadLine?,
    @SerialName("multimedia")
    val multimedia: List<MultiMedia>?
)

@Serializable
data class HeadLine(
    @SerialName("main")
    val main: String?
)

@Serializable
data class MultiMedia(
    @SerialName("url")
    val url: String?
)
