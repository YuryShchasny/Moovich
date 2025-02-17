package com.sb.moovich.core.exceptions

sealed class DataException: Exception() {
    data class NoMovieWithThisID(val id: Int?) : DataException()
    data class NoActorWithThisID(val id: Int?) : DataException()
}

sealed class ResponseExceptions: Exception() {
    data object NotAuthorized: ResponseExceptions()
    data object RequestLimit: ResponseExceptions()
    data object NotFound: ResponseExceptions()
    data object BadRequest: ResponseExceptions()
    data object ServerError: ResponseExceptions()
    data object Another: ResponseExceptions()
}