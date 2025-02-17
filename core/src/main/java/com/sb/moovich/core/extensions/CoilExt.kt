package com.sb.moovich.core.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.dispose
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.sb.moovich.core.R

fun ImageView.loadCoil(url: String?, onSuccess: (Drawable) -> Unit = {}) {
    this.load(url) {
        listener(object : ImageRequest.Listener {
            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                super.onSuccess(request, result)
                onSuccess.invoke(result.drawable)
            }
        })
    }
    if (url == null) {
        dispose()
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.poster_placeholder))
    }
}
