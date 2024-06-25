package com.sb.moovich.core.exceptions

sealed class DataException: Exception() {
    data class NoMovieWithThisID(val id: Int?) : DataException()
    data class NoActorWithThisID(val id: Int?) : DataException()
}