package com.sb.moovich.presentation.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.sb.moovich.presentation.onboarding.databinding.ItemOnboardingBinding

class OnboardingViewPagerAdapter(
    private var onboardingList: List<Onboarding>,
) : PagerAdapter() {

    override fun getCount(): Int = onboardingList.size

    override fun isViewFromObject(
        view: View,
        `object`: Any,
    ): Boolean = view == `object`

    override fun instantiateItem(
        container: ViewGroup,
        position: Int,
    ): Any {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            false
        )
        val onboarding = onboardingList[position]
        binding.title.text = onboarding.title
        binding.description.text = onboarding.description
        binding.root.setBackgroundResource(onboarding.imageResId)
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

data class Onboarding(
    val title: String,
    val description: String,
    val imageResId: Int
)
