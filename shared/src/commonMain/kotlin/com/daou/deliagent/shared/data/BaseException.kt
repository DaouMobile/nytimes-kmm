package com.daou.deliagent.shared.data

data class BaseException(
    val code: String? = "",
    val errorMessage: String? = ""
) : Exception()