package com.daou.deliagent.shared.data

import kotlinx.coroutines.CancellationException

data class BaseException(
    val code: String? = "",
    val errorMessage: String? = ""
) : CancellationException(errorMessage)