package com.sb.moovich.presentation.home.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.home.R
import com.sb.moovich.presentation.home.ui.view.NumberView
import kotlin.math.abs

class CustomCarouselLayoutManager(
    context: Context,
    private val recyclerView: RecyclerView
) : LinearLayoutManager(context, HORIZONTAL, false) {


    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        updateChildren()
        return scrolled
    }

    private fun updateChildren() {
        val mid = width / 2f
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childMid = getChildMid(child!!)
            val d = childMid - mid
            child.rotation = (d / mid) * 8f
            child.y = abs(child.rotation) * 5f
            val alpha = 1f - abs(d / mid) + 0.25f
            child.alpha = alpha
        }
    }

    fun initiateChildrenUpdate() {
        updateChildren()
        adjustToCenter(false)
    }

    private fun adjustToCenter(smooth: Boolean) {
        val mid = width / 2
        var closestChild: View? = null
        var minDistance = Int.MAX_VALUE

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childMid = getChildMid(child!!)
            val distance = abs(mid - childMid)

            if (distance < minDistance) {
                minDistance = distance
                closestChild = child
            }
        }

        closestChild?.let {
            val childMid = getChildMid(it)
            val distance = childMid - mid
            if (distance != 0) {
                if (smooth) {
                    recyclerView.smoothScrollBy(distance, 0)
                } else {
                    recyclerView.scrollBy(distance, 0)
                }
            }
        }
    }

    private fun getChildMid(child: View): Int {
        return (getDecoratedLeft(child) + getDecoratedRight(child)) / 2
    }

}