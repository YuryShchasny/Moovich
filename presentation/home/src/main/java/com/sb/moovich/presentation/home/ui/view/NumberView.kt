package com.sb.moovich.presentation.home.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.sb.moovich.core.extensions.dpToPx

class NumberView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var text: String = ""
    private var bitmap: Bitmap? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setBitmap(
            ContextCompat.getDrawable(
                context,
                com.sb.moovich.core.R.drawable.poster_placeholder
            )!!.toBitmap()
        )
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        bitmap?.let {
            val strokeWidth = 4.dpToPx()
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.textSize = 110.dpToPx().toFloat()
            paint.setTypeface(Typeface.DEFAULT_BOLD)
            val path = Path()
            paint.getTextPath(text, 0, text.length, 0f, height.toFloat() - strokeWidth, path)

            canvas.save()
            canvas.clipPath(path)

            canvas.drawBitmap(cropCenter(it, width, height), 0f, 0f, null)

            canvas.restore()

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidth.toFloat()
            paint.color = Color.WHITE
            canvas.drawPath(path, paint)
        }
        super.onDraw(canvas)
    }

    private fun cropCenter(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        val x = (bitmap.width - targetWidth) / 2
        val y = (bitmap.height - targetHeight) / 2
        return if (x < 0 && y < 0) Bitmap.createBitmap(bitmap, x, y, targetWidth, targetHeight)
        else bitmap
    }
}