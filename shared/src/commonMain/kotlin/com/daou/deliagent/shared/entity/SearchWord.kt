package com.daou.deliagent.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchWord(
    @SerialName("id")
    var id: Long,
    @SerialName("keyword")
    var keyword: String
)
