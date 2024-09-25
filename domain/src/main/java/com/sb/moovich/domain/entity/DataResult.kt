package com.sb.moovich.domain.entity

sealed class DataResult {
    data object Success : DataResult()
    data class Error(val type: ErrorType, val message: String = ""): DataResult()
}