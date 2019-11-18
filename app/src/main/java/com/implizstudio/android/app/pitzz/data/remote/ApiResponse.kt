package com.implizstudio.android.app.pitzz.data.remote

sealed class ApiResponse<T> {

    data class OnSuccess<T>(val body: T?) : ApiResponse<T>()
    data class OnFailure<T>(val code: Int?) : ApiResponse<T>()
    data class OnError<T>(val error: Throwable) : ApiResponse<T>()

}