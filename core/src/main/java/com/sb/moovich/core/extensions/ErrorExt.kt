package com.sb.moovich.core.extensions

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.sb.moovich.core.R
import com.sb.moovich.domain.entity.DataResult
import com.sb.moovich.domain.entity.ErrorType

fun DataResult.Error.showMessage(context: Context) {
    val message = when(this.type) {
        ErrorType.NotAuthorized -> ContextCompat.getString(context, R.string.auth_error)
        ErrorType.ServerError -> ContextCompat.getString(context, R.string.server_error)
        ErrorType.BadRequest -> ContextCompat.getString(context, R.string.bad_request_error)
        ErrorType.NotFound -> ContextCompat.getString(context, R.string.not_found_error)
        ErrorType.Another -> context.getString(R.string.another_error, message)
        ErrorType.RequestLimit -> ContextCompat.getString(context, R.string.request_limit)
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}