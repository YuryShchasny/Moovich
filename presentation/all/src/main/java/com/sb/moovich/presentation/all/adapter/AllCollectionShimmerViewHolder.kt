package com.sb.moovich.presentation.all.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.all.databinding.ItemAllCollectionShimmerBinding

class AllCollectionShimmerViewHolder (
    private val binding: ItemAllCollectionShimmerBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val progressAnimation =
        AlphaAnimation(ANIMATION_FADE_MIN, ANIMATION_FADE_MAX)
            .apply {
                duration = ANIMATION_FADE_TOTAL_DURATION
                interpolator = CycleInterpolator(1f)
                repeatMode = Animation.INFINITE
                repeatCount = Animation.INFINITE
            }

    fun bind() {
        binding.shimmerContent.startAnimation(progressAnimation)
    }

    companion object {
        fun from(parent: ViewGroup): AllCollectionShimmerViewHolder {
            return AllCollectionShimmerViewHolder(
                ItemAllCollectionShimmerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        private const val ANIMATION_FADE_MIN: Float = 0.7f
        private const val ANIMATION_FADE_MAX: Float = 1f
        private const val ANIMATION_FADE_TOTAL_DURATION: Long = 1000
    }
}
