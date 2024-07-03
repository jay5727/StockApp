package com.jay.stockapp.core.result

sealed class ResultState<out T> {
    data class Loading<T>(val isLoading: Boolean = true) : ResultState<T>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Throwable) : ResultState<Nothing>()
}