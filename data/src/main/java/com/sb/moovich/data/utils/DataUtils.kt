package com.sb.moovich.data.utils

import com.sb.moovich.core.exceptions.ResponseExceptions
import retrofit2.Response

suspend fun <T, D> Response<T>.process(mapping: suspend (body: T?) -> D): D {
    val error = when (val code = code()) {
        200 -> null
        400 -> ResponseExceptions.BadRequest
        401 -> ResponseExceptions.NotAuthorized
        403 -> ResponseExceptions.RequestLimit
        404 -> ResponseExceptions.NotFound
        else -> {
            if (code in 500..599)
                ResponseExceptions.ServerError
            else ResponseExceptions.Another
        }
    }
    error?.let { throw it }
    return mapping.invoke(body())
}

fun <T> Response<T>.process(): Response<T> {
    val error = when (val code = code()) {
        200 -> null
        400 -> ResponseExceptions.BadRequest
        401 -> ResponseExceptions.NotAuthorized
        403 -> ResponseExceptions.RequestLimit
        404 -> ResponseExceptions.NotFound
        else -> {
            if (code in 500..599)
                ResponseExceptions.ServerError
            else ResponseExceptions.Another
        }
    }
    error?.let { throw it }
    return this
}
