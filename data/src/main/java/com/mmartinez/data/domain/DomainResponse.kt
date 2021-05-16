package com.mmartinez.data.domain

sealed class DomainResponse<out SUCCESS, out ERROR> {
    data class Success<out SUCCESS, out ERROR>(val model: SUCCESS) : DomainResponse<SUCCESS, ERROR>()
    data class Error<out SUCCESS, out ERROR>(val model: ERROR) : DomainResponse<SUCCESS, ERROR>()
    @Suppress("unused")
    class Loading<out SUCCESS, out ERROR> : DomainResponse<SUCCESS, ERROR>()
}