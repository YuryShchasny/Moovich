package com.sb.moovich.presentation.info.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sb.moovich.core.extensions.loadCoil
import java.util.Locale

@BindingAdapter("load")
fun bindImageView(
    imageView: ImageView,
    imageUrl: String?,
) {
    imageView.loadCoil(imageUrl)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("stringList")
fun bindStringList(
    textView: TextView,
    genres: List<String>?,
) {
    textView.text = genres?.joinToString { it }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("stringTime")
fun bindTime(
    textView: TextView,
    movieLength: Int?,
) {
    movieLength?.let { length ->
        if (length <= 0) textView.visibility = View.GONE
        val hoursChar = textView.context.getString(com.sb.moovich.core.R.string.hours)
        val minutesChar = textView.context.getString(com.sb.moovich.core.R.string.minutes)
        textView.text = "${length / 60} $hoursChar ${length % 60} $minutesChar"
    }
}

@BindingAdapter("year")
fun bindYear(
    textView: TextView,
    year: Int?,
) {
    year?.let {
        if (year != 0) {
            textView.text = year.toString()
        }
    }
}

@BindingAdapter("rating")
fun bindRating(
    textView: TextView,
    rating: Double?,
) {
    rating?.let {
        if (rating == 0.0) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text = String.format(Locale.getDefault(), "%.1f", rating)
        }
    }
}