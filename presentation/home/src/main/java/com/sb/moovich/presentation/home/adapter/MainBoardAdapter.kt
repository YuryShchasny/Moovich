package com.sb.moovich.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.sb.moovich.core.extensions.loadCoil
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.home.databinding.ItemMainboardBinding

class MainBoardAdapter(private val list: List<Movie>, private val onClickListener: (Movie) -> Unit) : PagerAdapter() {

    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`


    override fun instantiateItem(
        container: ViewGroup,
        position: Int,
    ): Any {
        val binding =
            ItemMainboardBinding.inflate(LayoutInflater.from(container.context), container, false)
        val item = list[position]

        binding.backdropImageView.loadCoil(item.poster)
        binding.titleTextView.text = item.name
        binding.descriptionTextView.text = item.description
        binding.root.setOnClickListener { onClickListener(item) }

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any,
    ) {
        container.removeView(`object` as View)
    }
}