package com.jay.stockapp.core

import com.jay.stockapp.core.result.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> performApiCall(apiCall: suspend () -> T): Flow<ResultState<T>> = flow {
    emit(ResultState.Loading(true))
    try {
        val result = apiCall()
        emit(ResultState.Success(result))
    } catch (e: Exception) {
        emit(ResultState.Error(e))
    }finally {
        emit(ResultState.Loading(false))
    }
}
