package com.sb.moovich.core.extensions

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.sb.moovich.domain.entity.DataResult
import com.sb.moovich.domain.entity.ErrorType

fun DataResult.Error.showMessage(context: Context) {
    val message = when(this.type) {
        ErrorType.NotAuthorized -> ContextCompat.getString(context, com.sb.moovich.core.R.string.auth_error)
        ErrorType.ServerError -> ContextCompat.getString(context, com.sb.moovich.core.R.string.server_error)
        ErrorType.BadRequest -> ContextCompat.getString(context, com.sb.moovich.core.R.string.bad_request_error)
        ErrorType.NotFound -> ContextCompat.getString(context, com.sb.moovich.core.R.string.not_found_error)
        ErrorType.Another -> context.getString(com.sb.moovich.core.R.string.another_error, message)
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}