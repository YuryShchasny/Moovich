package com.sb.moovich.core.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.sb.moovich.core.R

class SpinnerAdapter(
    context: Context,
    private val resource: Int,
    var items: List<SpinnerItem>,
) : ArrayAdapter<SpinnerItem>(context, resource, items) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var onItemCheckBoxChanged: ((String, Boolean) -> Unit)? = null

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {
        return createViewFromResource(inflater, convertView, parent, position)
    }

    fun updateItems(items: List<SpinnerItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun createViewFromResource(
        inflater: LayoutInflater,
        convertView: View?,
        parent: ViewGroup,
        position: Int,
    ): View {
        val view: View = convertView ?: inflater.inflate(resource, parent, false)
        try {
            val item = items[position]
            val countryTextView: TextView = view.findViewById(R.id.countryTextView)
            countryTextView.text = item.country
            if (resource == R.layout.item_spinner_dropdown) {
                val checkbox: CheckBox = view.findViewById(R.id.checkboxCountry)
                checkbox.isChecked = item.isChecked
                checkbox.isClickable = false
                view.setOnClickListener {
                    onItemCheckBoxChanged?.invoke(item.country, !checkbox.isChecked)
                }
            }
        } catch (_: Exception) {
        }
        return view
    }
}

data class SpinnerItem(val country: String, val isChecked: Boolean)
