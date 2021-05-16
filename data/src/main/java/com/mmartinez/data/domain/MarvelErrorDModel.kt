package com.mmartinez.data.domain

data class MarvelErrorDModel(
    val errorCode: ErrorCode,
    val message: String? = null
)

sealed class ErrorCode {
    object ServerNotReachable : ErrorCode()
    object Unknown : ErrorCode()
}