package com.sb.moovich.core.views.shimmer

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import java.lang.Math.toRadians
import kotlin.math.sqrt
import kotlin.math.tan

class ShimmerDrawable : Drawable() {
    private val mUpdateListener = ValueAnimator.AnimatorUpdateListener {
        invalidateSelf()
    }

    private val mShimmerPaint = Paint().apply { isAntiAlias = true }
    private val mDrawRect = Rect()
    private val mShaderMatrix = Matrix()

    private var mValueAnimator: ValueAnimator? = null
    private var mStaticAnimationProgress = -1f

    private var mShimmer: Shimmer? = null

    fun setShimmer(shimmer: Shimmer?) {
        mShimmer = shimmer
        mShimmer?.let {
            mShimmerPaint.xfermode = PorterDuffXfermode(
                if (it.alphaShimmer) PorterDuff.Mode.DST_IN else PorterDuff.Mode.SRC_IN
            )
        }
        updateShader()
        updateValueAnimator()
        invalidateSelf()
    }

    fun getShimmer(): Shimmer? {
        return mShimmer
    }

    fun startShimmer() {
        if (mValueAnimator != null && !isShimmerStarted && callback != null) {
            mValueAnimator!!.start()
        }
    }

    fun stopShimmer() {
        if (mValueAnimator != null && isShimmerStarted) {
            mValueAnimator!!.cancel()
        }
    }

    val isShimmerStarted: Boolean
        get() = mValueAnimator?.isStarted ?: false

    val isShimmerRunning: Boolean
        get() = mValueAnimator?.isRunning ?: false

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        mDrawRect.set(bounds)
        updateShader()
        maybeStartShimmer()
    }

    fun setStaticAnimationProgress(value: Float) {
        if (value.compareTo(mStaticAnimationProgress) == 0 || (value < 0f && mStaticAnimationProgress < 0f)) {
            return
        }
        mStaticAnimationProgress = value.coerceAtMost(1f)
        invalidateSelf()
    }

    fun clearStaticAnimationProgress() {
        setStaticAnimationProgress(-1f)
    }

    override fun draw(canvas: Canvas) {
        mShimmer?.let { shimmer ->
            mShimmerPaint.shader?.let {
                val tiltTan = tan(toRadians(shimmer.tilt.toDouble())).toFloat()
                val translateHeight = mDrawRect.height() + tiltTan * mDrawRect.width()
                val translateWidth = mDrawRect.width() + tiltTan * mDrawRect.height()

                val dx: Float
                val dy: Float
                val animatedValue = if (mStaticAnimationProgress < 0f) {
                    mValueAnimator?.animatedValue as Float? ?: 0f
                } else {
                    mStaticAnimationProgress
                }

                when (shimmer.direction) {
                    Shimmer.Direction.LEFT_TO_RIGHT -> {
                        dx = offset(-translateWidth, translateWidth, animatedValue)
                        dy = 0f
                    }
                    Shimmer.Direction.RIGHT_TO_LEFT -> {
                        dx = offset(translateWidth, -translateWidth, animatedValue)
                        dy = 0f
                    }
                    Shimmer.Direction.TOP_TO_BOTTOM -> {
                        dx = 0f
                        dy = offset(-translateHeight, translateHeight, animatedValue)
                    }
                    Shimmer.Direction.BOTTOM_TO_TOP -> {
                        dx = 0f
                        dy = offset(translateHeight, -translateHeight, animatedValue)
                    }
                }

                mShaderMatrix.reset()
                mShaderMatrix.setRotate(shimmer.tilt, mDrawRect.width() / 2f, mDrawRect.height() / 2f)
                mShaderMatrix.preTranslate(dx, dy)
                mShimmerPaint.shader!!.setLocalMatrix(mShaderMatrix)

                canvas.drawRect(mDrawRect, mShimmerPaint)
            }
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return mShimmer?.let {
            if (it.clipToChildren || it.alphaShimmer) PixelFormat.TRANSLUCENT else PixelFormat.OPAQUE
        } ?: PixelFormat.OPAQUE
    }

    private fun offset(start: Float, end: Float, percent: Float): Float {
        return start + (end - start) * percent
    }

    private fun updateValueAnimator() {
        mShimmer?.let { shimmer ->
            val started = mValueAnimator?.isStarted ?: false
            mValueAnimator?.run {
                cancel()
                removeAllUpdateListeners()
            }

            mValueAnimator = ValueAnimator.ofFloat(
                0f,
                1f + shimmer.repeatDelay / shimmer.animationDuration.toFloat()
            ).apply {
                interpolator = LinearInterpolator()
                repeatMode = shimmer.repeatMode
                startDelay = shimmer.startDelay
                repeatCount = shimmer.repeatCount
                duration = (shimmer.animationDuration + shimmer.repeatDelay)
                addUpdateListener(mUpdateListener)
            }

            if (started) {
                mValueAnimator!!.start()
            }
        }
    }

    fun maybeStartShimmer() {
        if (mValueAnimator?.isStarted == false && mShimmer?.autoStart == true && callback != null) {
            mValueAnimator!!.start()
        }
    }

    private fun updateShader() {
        val bounds = bounds
        val boundsWidth = bounds.width()
        val boundsHeight = bounds.height()
        if (boundsWidth == 0 || boundsHeight == 0 || mShimmer == null) {
            return
        }

        val width = mShimmer!!.width(boundsWidth)
        val height = mShimmer!!.height(boundsHeight)

        val shader: Shader = when (mShimmer!!.shape) {
            Shimmer.Shape.LINEAR -> {
                val vertical = mShimmer!!.direction == Shimmer.Direction.TOP_TO_BOTTOM || mShimmer!!.direction == Shimmer.Direction.BOTTOM_TO_TOP
                val endX = if (vertical) 0 else width
                val endY = if (vertical) height else 0

                LinearGradient(0f, 0f, endX.toFloat(), endY.toFloat(), mShimmer!!.colors, mShimmer!!.positions, Shader.TileMode.CLAMP)
            }
            Shimmer.Shape.RADIAL -> {
                RadialGradient(
                    width / 2f,
                    height / 2f,
                    (width.coerceAtLeast(height) / sqrt(2.0)).toFloat(),
                    mShimmer!!.colors,
                    mShimmer!!.positions,
                    Shader.TileMode.CLAMP
                )
            }
        }

        mShimmerPaint.shader = shader
    }
}
