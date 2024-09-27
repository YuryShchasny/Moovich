package com.sb.moovich.presentation.search.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.slider.RangeSlider
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.extensions.dpToPx
import com.sb.moovich.core.views.SpinnerAdapter
import com.sb.moovich.core.views.SpinnerItem
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.MovieType
import com.sb.moovich.domain.entity.SortType
import com.sb.moovich.presentation.search.adapter.GenreItem
import com.sb.moovich.presentation.search.adapter.GenreListAdapter
import com.sb.moovich.presentation.search.databinding.FragmentFilterBinding
import com.sb.moovich.presentation.search.ui.model.filter.FilterFragmentEvent
import com.sb.moovich.presentation.search.ui.model.filter.FilterFragmentState
import com.sb.moovich.presentation.search.viewmodel.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>() {

    private val viewModel: FilterViewModel by viewModels()
    private val genreListAdapter = GenreListAdapter()
    private val sortViewList by lazy {
        listOf(binding.sortByPopularity, binding.sortByLatest, binding.sortByRating)
    }
    private var rangesSet = false

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.genreRecyclerView.adapter = genreListAdapter
        setClickListeners()
        observeState()
    }

    private fun observeState() {
        collectWithLifecycle(viewModel.state) { state ->
            when (state) {
                is FilterFragmentState.Content -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    if (!rangesSet) {
                        setRanges(state.filter)
                        rangesSet = true
                    }
                    val filter = state.filter
                    setGenreList(filter, state.genres)
                    setCountrySpinner(filter, state.countries)
                    setTabLayout(filter)
                    setApplyButton(filter)
                    setSortBy(filter)
                }

                FilterFragmentState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.GONE
                }
            }
        }
    }

    private fun setTabLayout(filter: Filter) {
        val position = MovieType.entries.indexOf(filter.type)
        binding.typeChooser.selectTab(binding.typeChooser.getTabAt(position))
        binding.typeChooser.setOnTabClickListener { tabPosition ->
            viewModel.fetchEvent(FilterFragmentEvent.OnTabClick(MovieType.entries[tabPosition]))
        }
    }

    private fun setGenreList(filter: Filter, genreList: List<String>) {
        val genres = genreList.map {
            GenreItem(it, filter.genres.contains(it))
        }
        genreListAdapter.submitList(genres)
        genreListAdapter.onItemClickListener = { genre ->
            viewModel.fetchEvent(FilterFragmentEvent.OnGenreClick(genre.genre))
        }
    }

    private fun setCountrySpinner(filter: Filter, countryList: List<String>) {
        val countries = countryList.map {
            SpinnerItem(it, filter.countries.contains(it))
        }
        binding.countrySpinner.setPopupAdapter(
            SpinnerAdapter(
                requireContext(), com.sb.moovich.core.R.layout.item_spinner_dropdown, countries
            )
        )
        binding.countrySpinner.setClickListener { isPopupShowing ->
            if (isPopupShowing) binding.countrySpinner.dismissPopup()
            else binding.countrySpinner.showPopup()
        }
        binding.countrySpinner.popUpAdapter?.onCountryClick = { country ->
            viewModel.fetchEvent(FilterFragmentEvent.OnCountryClick(country))
        }
        val checkedCountry = filter.countries
        val countryText =
            if (checkedCountry.isNotEmpty()) {
                binding.selectedCountryTextView.setTextColor(getColorCompat(com.sb.moovich.core.R.color.secondary))
                binding.countryTextView.setTextColor(getColorCompat(com.sb.moovich.core.R.color.secondary))
                if (checkedCountry.size > 1) checkedCountry.size.toString()
                else checkedCountry.firstOrNull()
            } else {
                binding.selectedCountryTextView.setTextColor(getColorCompat(com.sb.moovich.core.R.color.white))
                binding.countryTextView.setTextColor(getColorCompat(com.sb.moovich.core.R.color.white))
                getStringCompat(com.sb.moovich.core.R.string.all)
            }
        binding.selectedCountryTextView.text = countryText
    }

    private fun setSortBy(filter: Filter) {
        sortViewList.forEachIndexed { index, textView ->
            (textView.background as GradientDrawable).apply {
                val newColor =
                    if (SortType.entries.indexOf(filter.sortType) == index)
                        getColorCompat(com.sb.moovich.core.R.color.secondary)
                    else Color.TRANSPARENT
                val newStrokeColor = if (SortType.entries.indexOf(filter.sortType) == index)
                    getColorCompat(com.sb.moovich.core.R.color.secondary)
                else getColorCompat(com.sb.moovich.core.R.color.white)
                color = ColorStateList.valueOf(newColor)
                setStroke(1.dpToPx(), newStrokeColor)
            }
        }
    }

    private fun setClickListeners() {
        binding.buttonBack.setOnClickListener { viewModel.fetchEvent(FilterFragmentEvent.OnBackPressed) }
        binding.buttonReset.setOnClickListener { viewModel.fetchEvent(FilterFragmentEvent.Reset) }
        sortViewList.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.fetchEvent(FilterFragmentEvent.OnSortViewClick(SortType.entries[index]))
            }
        }
        binding.applyButton.setOnClickListener {
            viewModel.fetchEvent(FilterFragmentEvent.SaveFilter)
        }
    }

    private fun setApplyButton(filter: Filter) {
        binding.applyButton.apply {
            if (filter.hasFilters()) {
                setCardBackgroundColor(getColorCompat(com.sb.moovich.core.R.color.primary))
                isClickable = true
            } else {
                setCardBackgroundColor(getColorCompat(com.sb.moovich.core.R.color.pre_black))
                isClickable = false
            }
        }
        binding.applyTextView.text = String.format(
            Locale.getDefault(),
            getStringCompat(com.sb.moovich.core.R.string.apply_filters),
            filter.getFiltersCount()
        )
    }


    private fun setRanges(filter: Filter) {
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
        binding.rangeYearSlider.values = listOf(filter.yearFrom.toFloat(), filter.yearTo.toFloat())
        binding.rangeRatingSlider.values = listOf(filter.ratingFrom.toFloat(), filter.ratingTo.toFloat())
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
        startTextView.text = valueFrom.toInt().toString()
        endTextView.text = valueTo.toInt().toString()
        range.addOnChangeListener { slider, _, _ ->
            startTextView.text = slider.values.first().toInt().toString()
            endTextView.text = slider.values.last().toInt().toString()
            viewModel.fetchEvent(
                FilterFragmentEvent.UpdateSliders(
                    binding.rangeYearSlider.values.first().toInt(),
                    binding.rangeYearSlider.values.last().toInt(),
                    binding.rangeRatingSlider.values.first().toInt(),
                    binding.rangeRatingSlider.values.last().toInt(),
                )
            )
        }
    }

    companion object {
        private const val YEAR_FROM = 1937f
    }
}
