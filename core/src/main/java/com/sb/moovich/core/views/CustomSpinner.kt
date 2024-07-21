package com.sb.moovich.core.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import com.sb.moovich.core.R

class CustomSpinner(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    private val listPopupWindow: ListPopupWindow = ListPopupWindow(context, attrs)
    private var clickListener: ((Boolean) -> Unit)? = null
    var popUpAdapter: SpinnerAdapter? = null

    init {
        this.setOnClickListener {
            performClick()
        }
        listPopupWindow.anchorView = this
        listPopupWindow.isModal = true
        listPopupWindow.height = 900
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
        return true
    }

    fun setPopupAdapter(adapter: SpinnerAdapter) {
        this.popUpAdapter = adapter
        listPopupWindow.setAdapter(adapter)
    }

    fun getPopupScrolledOn(): Int {
        return listPopupWindow.selectedItemPosition
    }

    fun updateItems(items: List<SpinnerItem>) {
        popUpAdapter?.updateItems(items)
    }

    fun setClickListener(listener: (Boolean) -> Unit) {
        this.clickListener = listener
    }
    fun showPopup() {
        listPopupWindow.show()
    }
    fun dismissPopup() {
        listPopupWindow.dismiss()
    }
}
