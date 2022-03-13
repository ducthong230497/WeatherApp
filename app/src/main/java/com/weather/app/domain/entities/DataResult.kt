package com.weather.app.domain.entities

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

data class DataResult<out T>(val status: Status, val data: T?, val error: Throwable?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): DataResult<T> {
            return DataResult(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable, data: T? = null): DataResult<T> {
            return DataResult(Status.ERROR, data, error)
        }

        fun <T> loading(data: T? = null): DataResult<T> {
            return DataResult(Status.LOADING, data, null)
        }
    }
}

fun <T> Flow<DataResult<T>>.filterSuccessOrError(): Flow<DataResult<T>> {
    return filter { it.status == DataResult.Status.ERROR || it.status == DataResult.Status.SUCCESS }
}
