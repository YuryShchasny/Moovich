package com.sb.moovich.core.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.sb.moovich.core.R
import kotlin.math.min

class SearchProgressBar(context: Context, attrs: AttributeSet) : ProgressBar(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = ContextCompat.getColor(context, R.color.primary)
    }

    private var rotationAngles = arrayOf(0f, 0f, 0f)

    private val animators = arrayOf(
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                rotationAngles[0] = animation.animatedValue as Float
                invalidate()
            }
            start()
        },
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                rotationAngles[1] = animation.animatedValue as Float
                invalidate()
            }
            start()
        },
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 600
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                rotationAngles[2] = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    )

    override fun onDraw(canvas: Canvas) {
        val center = arrayOf(width / 2f, height / 2f)
        val radius1 = min(center[0], center[1]) - 10f
        val radius2 = radius1 - radius1 * 0.3f
        val radius3 = radius2 - radius1 * 0.3f
        listOf(radius1, radius2, radius3).forEachIndexed { index, radius ->
            canvas.drawArc(
                center[0] - radius,
                center[1] - radius,
                center[0] + radius,
                center[1] + radius,
                rotationAngles[index],
                90f,
                false,
                paint
            )
        }
    }

    override fun onDetachedFromWindow() {
        animators.forEach { it.cancel() }
        super.onDetachedFromWindow()
    }
}