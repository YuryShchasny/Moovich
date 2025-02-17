package com.sb.moovich.core.extensions

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.sb.moovich.core.R
import com.sb.moovich.core.exceptions.ResponseExceptions

fun ResponseExceptions.showMessage(parent: View) {
    val context = parent.context
    val message = when (this) {
        ResponseExceptions.Another -> context.getString(R.string.another_error, message ?: "")
        ResponseExceptions.NotAuthorized -> ContextCompat.getString(context, R.string.auth_error)
        ResponseExceptions.RequestLimit -> ContextCompat.getString(context, R.string.request_limit)
        ResponseExceptions.ServerError -> ContextCompat.getString(context, R.string.server_error)
        ResponseExceptions.NotFound -> ContextCompat.getString(context, R.string.not_found_error)
        ResponseExceptions.BadRequest -> ContextCompat.getString(
            context,
            R.string.bad_request_error
        )
    }
    val snackBar = Snackbar.make(context, parent, message, Snackbar.LENGTH_INDEFINITE).apply {
        setAction(context.getString(R.string.its_clear)) {
            this.dismiss()
        }
        setActionTextColor(context.getColor(R.color.primary))
        setTextColor(context.getColor(R.color.white))
        setTextMaxLines(3)
        setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
    }
    snackBar.show()
}