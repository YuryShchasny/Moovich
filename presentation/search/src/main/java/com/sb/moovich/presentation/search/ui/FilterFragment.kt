package com.sb.moovich.presentation.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.entexy.core.view.SpinnerAdapter
import com.entexy.core.view.SpinnerItem
import com.google.android.material.slider.RangeSlider
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.presentation.search.R
import com.sb.moovich.presentation.search.adapter.GenreItem
import com.sb.moovich.presentation.search.adapter.GenreListAdapter
import com.sb.moovich.presentation.search.databinding.FragmentFilterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class FilterFragment : BaseFragment<FragmentFilterBinding>() {
    private val genreListAdapter = GenreListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.typeChooser.selectTab(binding.typeChooser.getTabAt(0))
        setRanges()
        setCountrySpinner(
            listOf(
                SpinnerItem("USA", false),
                SpinnerItem("Russia", false),
                SpinnerItem("Ukraine", false),
                SpinnerItem("Belarus", false),
                SpinnerItem("Poland", false),
                SpinnerItem("Italy", false),
            )
        )
        setGenreList(
            listOf(
                GenreItem("All", false),
                GenreItem("Family", false),
                GenreItem("Action", false),
                GenreItem("Fantasy", false),
                GenreItem("Adventure", false),
                GenreItem("Fantastic", false),
                GenreItem("Anime", false),
                GenreItem("History", false),
                GenreItem("Horror", false),
                GenreItem("All", false),
                GenreItem("Family", false),
                GenreItem("Action", false),
                GenreItem("Fantasy", false),
                GenreItem("Adventure", false),
                GenreItem("Fantastic", false),
                GenreItem("Anime", false),
                GenreItem("History", false),
                GenreItem("Horror", false),
            )
        )
    }

    private fun setGenreList(genreList: List<GenreItem>) {
        binding.genreRecyclerView.adapter = genreListAdapter
        genreListAdapter.submitList(genreList)
    }

    private fun setCountrySpinner(countryList: List<SpinnerItem>) {
        val checkedCountry = countryList.filter { it.isChecked }
        val countryText =
            if (checkedCountry.isNotEmpty()) {
                binding.selectedCountryTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.sb.moovich.core.R.color.secondary
                    )
                )
                binding.countryTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.sb.moovich.core.R.color.secondary
                    )
                )
                if (checkedCountry.size > 1) checkedCountry.size.toString()
                else checkedCountry.first().country
            } else {
                binding.selectedCountryTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.sb.moovich.core.R.color.white
                    )
                )
                binding.countryTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.sb.moovich.core.R.color.white
                    )
                )
                ContextCompat.getString(requireContext(), com.sb.moovich.core.R.string.all)
            }
        binding.selectedCountryTextView.text = countryText
        binding.countrySpinner.setPopupAdapter(
            SpinnerAdapter(
                requireContext(), com.sb.moovich.core.R.layout.item_spinner_dropdown, countryList
            )
        )
        binding.countrySpinner.setPopupHeight(600)
        binding.countrySpinner.setClickListener { isPopupShowing ->
            val newHeight = if (isPopupShowing) 0 else 600
            binding.spaceForPopup.updateLayoutParams { height = newHeight }
            CoroutineScope(Dispatchers.Main).launch {
                while (binding.spaceForPopup.height != newHeight) {
                    delay(100)
                }
                binding.countrySpinner.showPopup()
            }
        }
    }

    private fun setRanges() {
        setRange(
            binding.rangeYearSlider,
            YEAR_FROM,
            Calendar.getInstance().get(Calendar.YEAR).toFloat(),
            binding.startYearTextView,
            binding.endYearTextView
        )
        setRange(
            binding.rangeRatingSlider,
            0f,
            10f,
            binding.startRatingTextView,
            binding.endRatingTextView
        )
    }

    private fun setRange(
        range: RangeSlider,
        valueFrom: Float,
        valueTo: Float,
        startTextView: TextView,
        endTextView: TextView
    ) {
        range.stepSize = 1f
        range.isTickVisible = false
        range.valueFrom = valueFrom
        range.valueTo = valueTo
        range.values = mutableListOf(valueFrom, valueTo)
        startTextView.text = valueFrom.toInt().toString()
        endTextView.text = valueTo.toInt().toString()
        range.addOnChangeListener { slider, _, _ ->
            startTextView.text = slider.values.first().toInt().toString()
            endTextView.text = slider.values.last().toInt().toString()
        }
    }

    companion object {
        private const val YEAR_FROM = 1937f
    }
}