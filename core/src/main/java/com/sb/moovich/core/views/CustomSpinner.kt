package com.sb.moovich.core.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.entexy.core.view.SpinnerAdapter
import com.entexy.core.view.SpinnerItem
import com.sb.moovich.core.R
import com.sb.moovich.core.extensions.dpToPx

class CustomSpinner(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    private val listPopupWindow: ListPopupWindow = ListPopupWindow(context, attrs)

    private var itemList: List<SpinnerItem>? = null
    private var clickListener: ((Boolean) -> Unit)? = null

    init {
        this.setOnClickListener {
            performClick()
        }
        listPopupWindow.anchorView = this
        listPopupWindow.isModal = true
        listPopupWindow.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.custom_spinner_background,
            ),
        )
        listPopupWindow.setOnDismissListener {
            //TODO save genres
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun performClick(): Boolean {
        clickListener?.invoke(listPopupWindow.isShowing)
        if (listPopupWindow.isShowing) {
            listPopupWindow.dismiss()
        }
        return true
    }

    fun setPopupAdapter(adapter: SpinnerAdapter) {
        listPopupWindow.setAdapter(adapter)
        itemList = adapter.items
    }

    fun setPopupHeight(height: Int) {
        listPopupWindow.height = height
        listPopupWindow.verticalOffset = (-40).dpToPx()
    }

    fun setClickListener(listener: (Boolean) -> Unit) {
        this.clickListener = listener
    }
    fun showPopup() {
        listPopupWindow.show()
    }
}
