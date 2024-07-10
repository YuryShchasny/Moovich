package com.entexy.core.view

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
    val items: List<SpinnerItem>,
) : ArrayAdapter<SpinnerItem>(context, resource, items) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {
        return createViewFromResource(inflater, convertView, parent, position)
    }

    private fun createViewFromResource(
        inflater: LayoutInflater,
        convertView: View?,
        parent: ViewGroup,
        position: Int,
    ): View {
        val view: View = convertView ?: inflater.inflate(resource, parent, false)
        try {
            val item = getItem(position)
            val countryTextView: TextView = view.findViewById(R.id.countryTextView)
            item?.let {
                countryTextView.text = it.country
                if (resource == R.layout.item_spinner_dropdown) {
                    val checkbox: CheckBox = view.findViewById(R.id.checkboxCountry)
                    checkbox.isChecked = it.isChecked
                    view.setOnClickListener {
                        checkbox.isChecked = !checkbox.isChecked
                    }
                }
            }
        } catch (_: Exception) {
        }
        return view
    }
}

data class SpinnerItem(val country: String, val isChecked: Boolean)
