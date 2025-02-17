package com.sb.moovich.core.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.drawable.InsetDrawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import com.google.android.material.tabs.TabLayout
import com.sb.moovich.core.R
import com.sb.moovich.core.extensions.dpToPx
import kotlin.math.abs
import kotlin.math.min


class TypeChooserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    private val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.white)
        pathEffect = CornerPathEffect(4f)
    }
    private val dividerWidth = 1f.dpToPx()
    private val dividerHeight = 16f.dpToPx()
    private var indicatorDrawable = ContextCompat.getDrawable(context, R.drawable.tab_layout_indicator)
    private var selectedBeforeClick = 0
    private var onTabSelectedListener: ((Int) -> Unit)? = null

    init {
        setTabTextColors(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.white))
        tabRippleColor = null
        setSelectedTabIndicator(indicatorDrawable)
        addOnTabSelectedListener(object: OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                val insetDrawable = indicatorDrawable.let {
                    val insetLeft = if(selectedTabPosition == 0) 0 else -10
                    val insetRight = if(selectedTabPosition == 3) 0 else -10
                    InsetDrawable(it, insetLeft, 0, insetRight, 0)
                }
                setSelectedTabIndicator(insetDrawable)
                onTabSelectedListener?.invoke(tab?.position ?: 0)
            }
            override fun onTabUnselected(tab: Tab?) {}
            override fun onTabReselected(tab: Tab?) {}
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val tabWidth = (width - 40.dpToPx()) / tabCount
        repeat(tabCount) {
            getTabAt(it)?.view?.updateLayoutParams { width = tabWidth }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        repeat(tabCount - 1) {
            val tabView = getTabAt(it)?.view
            val location = IntArray(2)
            tabView?.getLocationOnScreen(location)
            val startX = location[0] - marginStart
            val width = tabView?.width ?: 0
            val endX = startX + width.toFloat()
            if(it != min(selectedTabPosition, selectedBeforeClick) || abs(selectedTabPosition - selectedBeforeClick) > 1) {
                canvas.drawRect(endX, (this.height / 2) - (dividerHeight / 2), endX + dividerWidth, (this.height / 2) + (dividerHeight / 2), dividerPaint)
            }
        }
        selectedBeforeClick = selectedTabPosition
    }

    fun setOnTabClickListener(listener:(Int)->Unit) {
        this.onTabSelectedListener = listener
    }
}