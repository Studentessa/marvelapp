package com.mmartinez.data.model

data class ResponseData<out T> (
    val code: Int,
    val status: String,
    val data: T
)