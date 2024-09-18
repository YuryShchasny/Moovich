package com.sb.moovich.core.views.shimmer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.sb.moovich.core.R

open class ShimmerFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val mContentPaint = Paint()
    private val mShimmerDrawable: ShimmerDrawable = ShimmerDrawable()

    /**
     * Return whether the shimmer drawable is visible.
     */
    private var isShimmerVisible = true
    private var mStoppedShimmerBecauseVisibility = false

    init {
        setWillNotDraw(false)
        mShimmerDrawable.callback = this
        if (attrs == null) {
            setShimmer(Shimmer.AlphaHighlightBuilder().build())
        } else {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ShimmerFrameLayout, 0, 0)
            try {
                val shimmerBuilder = if (a.hasValue(R.styleable.ShimmerFrameLayout_shimmer_colored)
                    && a.getBoolean(R.styleable.ShimmerFrameLayout_shimmer_colored, false)
                ) Shimmer.ColorHighlightBuilder() else Shimmer.AlphaHighlightBuilder()
                setShimmer(shimmerBuilder.consumeAttributes(a)?.build())
            } finally {
                a.recycle()
            }
        }
    }

    private fun setShimmer(shimmer: Shimmer?): ShimmerFrameLayout {
        mShimmerDrawable.setShimmer(shimmer)
        if (shimmer != null && shimmer.clipToChildren) {
            setLayerType(LAYER_TYPE_HARDWARE, mContentPaint)
        } else {
            setLayerType(LAYER_TYPE_NONE, null)
        }
        return this
    }

    val shimmer: Shimmer?
        get() = mShimmerDrawable.getShimmer()

    /**
     * Starts the shimmer animation.
     */
    private fun startShimmer() {
        if (isAttachedToWindow) {
            mShimmerDrawable.startShimmer()
        }
    }

    /**
     * Stops the shimmer animation.
     */
    private fun stopShimmer() {
        mStoppedShimmerBecauseVisibility = false
        mShimmerDrawable.stopShimmer()
    }

    val isShimmerStarted: Boolean
        /**
         * Return whether the shimmer animation has been started.
         */
        get() = mShimmerDrawable.isShimmerStarted

    /**
     * Sets the ShimmerDrawable to be visible.
     *
     * @param startShimmer Whether to start the shimmer again.
     */
    fun showShimmer(startShimmer: Boolean) {
        isShimmerVisible = true
        if (startShimmer) {
            startShimmer()
        }
        invalidate()
    }

    /**
     * Sets the ShimmerDrawable to be invisible, stopping it in the process.
     */
    fun hideShimmer() {
        stopShimmer()
        isShimmerVisible = false
        invalidate()
    }

    val isShimmerRunning: Boolean
        get() = mShimmerDrawable.isShimmerRunning

    public override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val width = width
        val height = height
        mShimmerDrawable.setBounds(0, 0, width, height)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        // View's constructor directly invokes this method, in which case no fields on
        // this class have been fully initialized yet.
        if (visibility != VISIBLE) {
            // GONE or INVISIBLE
            if (isShimmerStarted) {
                stopShimmer()
                mStoppedShimmerBecauseVisibility = true
            }
        } else if (mStoppedShimmerBecauseVisibility) {
            mShimmerDrawable.maybeStartShimmer()
            mStoppedShimmerBecauseVisibility = false
        }
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mShimmerDrawable.maybeStartShimmer()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopShimmer()
    }

    public override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (isShimmerVisible) {
            mShimmerDrawable.draw(canvas)
        }
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who === mShimmerDrawable
    }

    fun setStaticAnimationProgress(value: Float) {
        mShimmerDrawable.setStaticAnimationProgress(value)
    }

    fun clearStaticAnimationProgress() {
        mShimmerDrawable.clearStaticAnimationProgress()
    }
}
