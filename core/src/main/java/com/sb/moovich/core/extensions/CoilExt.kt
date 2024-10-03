package com.sb.moovich.core.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult

fun ImageView.load(url: String, onSuccess: (Drawable) -> Unit = {}) {
    this.load(url) {
        listener(object : ImageRequest.Listener {
            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                super.onSuccess(request, result)
                onSuccess.invoke(result.drawable)
            }
        })
    }
}
