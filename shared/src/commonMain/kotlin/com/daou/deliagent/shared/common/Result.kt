package com.daou.boyak.kmm.common.data

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Fail(val baseException: BaseException) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Fail -> "Error[code=${baseException.code}, message=${baseException.message ?: ""}]"
        }
    }
}