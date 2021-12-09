package com.daou.deliagent.shared.common

interface Mapper<in T, out E> {
    fun mapFrom(from: T): E
    fun mapAll(from: List<T>): List<E>
}