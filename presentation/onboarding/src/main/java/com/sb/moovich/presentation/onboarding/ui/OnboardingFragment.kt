package com.sb.moovich.presentation.onboarding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.extensions.dpToPx
import com.sb.moovich.presentation.onboarding.R
import com.sb.moovich.presentation.onboarding.adapter.Onboarding
import com.sb.moovich.presentation.onboarding.adapter.OnboardingViewPagerAdapter
import com.sb.moovich.presentation.onboarding.databinding.FragmentOnboardingBinding
import com.sb.moovich.presentation.onboarding.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    private val viewModel: OnboardingViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentOnboardingBinding = FragmentOnboardingBinding.inflate(inflater, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        startAnimation()
    }

    private fun startAnimation() {
        binding.titleTextView.apply {
            scaleX = 0f
            scaleY = 0f
        }
        binding.onboardingViewPager.alpha = 0f
        binding.root.post {
            binding.linearLayout.translationY = 100.dpToPx().toFloat()
            binding.titleTextView.translationY = binding.root.height / 2f
            binding.titleTextView.animate()
                .setDuration(2000)
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(OvershootInterpolator())
                .withEndAction {
                    binding.titleTextView.animate()
                        .setDuration(2000)
                        .translationY(0f)
                        .setInterpolator(DecelerateInterpolator())
                        .start()
                    binding.linearLayout.animate()
                        .setDuration(2000)
                        .translationY(0f)
                        .setInterpolator(DecelerateInterpolator())
                        .start()
                    binding.onboardingViewPager.animate()
                        .setDuration(2000)
                        .alpha(1f)
                        .setInterpolator(DecelerateInterpolator())
                        .start()
                }
                .start()
        }
    }

    private fun initView() {
        val onboardingList =
            listOf(
                Onboarding(
                    getStringCompat(com.sb.moovich.core.R.string.onboarding_title_1),
                    getStringCompat(com.sb.moovich.core.R.string.onboarding_description_1),
                    R.drawable.onboarding_1,
                ),
                Onboarding(
                    getStringCompat(com.sb.moovich.core.R.string.onboarding_title_2),
                    getStringCompat(com.sb.moovich.core.R.string.onboarding_description_2),
                    R.drawable.onboarding_2,
                ),
                Onboarding(
                    getStringCompat(com.sb.moovich.core.R.string.onboarding_title_3),
                    getStringCompat(com.sb.moovich.core.R.string.onboarding_description_3),
                    R.drawable.onboarding_3,
                ),
            )
        setOnClickListeners(onboardingList)
        binding.onboardingViewPager.adapter = OnboardingViewPagerAdapter(onboardingList)
        val listPoints =
            listOf(
                binding.point1,
                binding.point2,
                binding.point3,
            ).map {
                it.apply {
                    drawable.alpha = 255 / 2
                }
            }
        binding.onboardingViewPager.addOnPageChangeListener(
            object :
                ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    if (position + 1 != onboardingList.size) {
                        listPoints[position + 1].updateLayoutParams {
                            width = (10.dpToPx() + ((40 - 10) * positionOffset).dpToPx()).toInt()
                        }
                        listPoints[position + 1].drawable.alpha =
                            (255 / 2 + (255 / 2 * positionOffset)).toInt()
                    }
                    listPoints[position].updateLayoutParams {
                        width = (40.dpToPx() - ((40 - 10) * positionOffset).dpToPx()).toInt()
                    }
                    listPoints[position].drawable.alpha = (255 - (255 / 2 * positionOffset)).toInt()
                }

                override fun onPageSelected(position: Int) {
                    val buttonNextText =
                        if (position == onboardingList.size - 1) com.sb.moovich.core.R.string.awesome else com.sb.moovich.core.R.string.next
                    binding.nextButton.text =
                        ContextCompat.getString(binding.root.context, buttonNextText)
                }

                override fun onPageScrollStateChanged(state: Int) {}
            }
        )
        addOnBackPressedCallback {
            if (binding.onboardingViewPager.currentItem != 0) {
                binding.onboardingViewPager.setCurrentItem(binding.onboardingViewPager.currentItem - 1)
            } else requireActivity().finish()
        }
    }

    private fun setOnClickListeners(onboardingList: List<Onboarding>) {
        binding.nextButtonCardView.setOnClickListener {
            val currentItem = binding.onboardingViewPager.currentItem
            if (currentItem == onboardingList.size - 1) viewModel.finishOnboarding()
            else binding.onboardingViewPager.setCurrentItem(currentItem + 1, true)
        }
    }
}
