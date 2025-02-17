package com.sb.moovich.core.views

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.navOptions
import com.sb.moovich.core.R
import com.sb.moovich.core.extensions.dpToPx

class MoovichBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TOP_PADDING = 7
        private const val ICON_SIZE = 40
        private const val ANIMATION_DURATION = 200L
        private const val RECT_RADIUS = 25
    }

    private var navController: NavController? = null
    private var menu: List<MenuItem> = emptyList()
    private var selectedItem: MenuItem? = null
    private var selectedItemPrevious: MenuItem? = null
    private val iconsView: MutableList<View> = mutableListOf()
    private val unselectedColor = context.getColor(R.color.on_background)
    private val selectedColor = context.getColor(R.color.on_nav_bar)
    private var selectionColors = selectedColor to unselectedColor
    private var selectionTop = 0f
    private val animation: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = ANIMATION_DURATION
        addUpdateListener {
            selectionColors =
                getTransitionColors(selectedColor, unselectedColor, it.animatedValue as Float)
            selectionTop = TOP_PADDING.dpToPx().toFloat() * it.animatedFraction
            invalidate()
        }
    }

    private val backgroundPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.on_background)
    }

    init {
        setWillNotDraw(false)
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun setNavMenu(menu: List<MenuItem>) {
        this.menu = menu
        menu.forEachIndexed { index, menuItem ->
            val row = LinearLayout(context).apply {
                orientation = VERTICAL
                gravity = CENTER
                layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f).apply {
                    setMargins(
                        0,
                        TOP_PADDING.dpToPx(),
                        0,
                        0
                    )
                }
            }
            val iconView = ImageView(context).apply {
                layoutParams = LayoutParams(ICON_SIZE.dpToPx(), ICON_SIZE.dpToPx())
                setImageDrawable(menuItem.icon)
            }
            val textView = TextView(context).apply {
                text = menuItem.label
                gravity = CENTER_HORIZONTAL
                textSize = 12f
            }
            row.addView(iconView)
            row.addView(textView)
            row.setOnClickListener {
                navigate(menuItem.route)
            }
            iconsView.add(row)
            this.addView(row, index, row.layoutParams)
        }
    }

    fun setupWithNavController(navController: NavController) {
        this.navController = navController
        selectedItem = menu.find { it.route == navController.currentDestination?.route }
        selectedItem?.let { navigate(it.route) }
    }

    fun destinationChanged(destination: NavDestination) {
        onItemSelect(menu.find { it.route == destination.route }
            ?: throw IllegalArgumentException())
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val radius = RECT_RADIUS.dpToPx().toFloat()
        val rect = RectF(0f, TOP_PADDING.dpToPx().toFloat(), width.toFloat(), height.toFloat())
        drawTopRoundedRect(canvas, rect, radius, backgroundPaint)
        selectedItem?.let {
            val selectedIndex = menu.indexOf(selectedItem)
            iconsView.forEachIndexed { index, view ->
                if (index == selectedIndex) {
                    val selectRect =
                        RectF(
                            view.left.toFloat(),
                            TOP_PADDING.dpToPx() - selectionTop,
                            view.right.toFloat(),
                            height.toFloat()
                        )
                    val paint = Paint().apply { color = selectionColors.second }
                    drawTopRoundedRect(canvas, selectRect, radius, paint)
                }
            }
        }
        if (selectedItemPrevious != selectedItem) {
            selectedItemPrevious?.let {
                val selectedItemPreviousIndex = menu.indexOf(selectedItemPrevious)
                iconsView.forEachIndexed { index, view ->
                    if (index == selectedItemPreviousIndex) {
                        val selectRect =
                            RectF(
                                view.left.toFloat(),
                                selectionTop,
                                view.right.toFloat(),
                                height.toFloat()
                            )
                        val paint = Paint().apply { color = selectionColors.first }
                        drawTopRoundedRect(canvas, selectRect, radius, paint)
                    }
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        animation.cancel()
        super.onDetachedFromWindow()
    }

    private fun onItemSelect(
        item: MenuItem,
    ) {
        val index = menu.indexOf(item)
        iconsView.forEachIndexed { i, view ->
            val tint = if (index == i) R.color.primary else R.color.grey
            ((view as LinearLayout).children.first() as ImageView).setColorFilter(
                ContextCompat.getColor(
                    context,
                    tint
                )
            )
            (view.children.last() as TextView).setTextColor(ContextCompat.getColor(context, tint))
        }
        selectedItemPrevious = selectedItem
        selectedItem = item
        animation.start()
        invalidate()
    }


    private fun navigate(route: String) {
        val options = navOptions {
            launchSingleTop = true
            popUpTo(navController?.graph?.startDestinationId ?: 0) {
                saveState = true
            }
            restoreState = true
        }
        val destination = navController?.graph?.find { it.route == route }
        destination?.let {
            navController?.navigate(it.id, null, options)
        }
    }

    private fun drawTopRoundedRect(canvas: Canvas, rect: RectF, radius: Float, paint: Paint) {
        val path = Path()
        path.moveTo(rect.left, rect.bottom)
        path.lineTo(rect.left, rect.top + radius)
        path.quadTo(rect.left, rect.top, rect.left + radius, rect.top)
        path.lineTo(rect.right - radius, rect.top)
        path.quadTo(rect.right, rect.top, rect.right, rect.top + radius)
        path.lineTo(rect.right, rect.bottom)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun getTransitionColors(
        startColor: Int,
        endColor: Int,
        fraction: Float
    ): Pair<Int, Int> {
        val color1 = evaluateColor(startColor, endColor, fraction)
        val color2 = evaluateColor(endColor, startColor, fraction)
        return Pair(color1, color2)
    }

    private fun evaluateColor(startColor: Int, endColor: Int, fraction: Float): Int {
        val a =
            (Color.alpha(startColor) + (Color.alpha(endColor) - Color.alpha(startColor)) * fraction).toInt()
        val r =
            (Color.red(startColor) + (Color.red(endColor) - Color.red(startColor)) * fraction).toInt()
        val g =
            (Color.green(startColor) + (Color.green(endColor) - Color.green(startColor)) * fraction).toInt()
        val b =
            (Color.blue(startColor) + (Color.blue(endColor) - Color.blue(startColor)) * fraction).toInt()
        return Color.argb(a, r, g, b)
    }

    data class MenuItem(
        val route: String,
        val icon: Drawable,
        val label: String
    )
}