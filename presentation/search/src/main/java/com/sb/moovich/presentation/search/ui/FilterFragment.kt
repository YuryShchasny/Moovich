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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.slider.RangeSlider
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.extensions.dpToPx
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.core.views.SpinnerAdapter
import com.sb.moovich.core.views.SpinnerItem
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.MovieType
import com.sb.moovich.domain.entity.SortType
import com.sb.moovich.presentation.search.adapter.GenreItem
import com.sb.moovich.presentation.search.adapter.GenreListAdapter
import com.sb.moovich.presentation.search.databinding.FragmentFilterBinding
import com.sb.moovich.presentation.search.model.filter.FilterFragmentEvent
import com.sb.moovich.presentation.search.model.filter.FilterFragmentState
import com.sb.moovich.presentation.search.viewmodel.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>() {
    @Inject
    lateinit var navigation: INavigation
    private val viewModel: FilterViewModel by viewModels()
    private val genreListAdapter = GenreListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is FilterFragmentState.Content -> {
                            setRanges(state.filter)
                            setGenreList(state.filter, state.genres)
                            setCountrySpinner(state.filter, state.countries)
                            setClickListeners()
                            setTabLayout(state.filter)
                            binding.progressBar.visibility = View.GONE
                            binding.scrollView.visibility = View.VISIBLE
                            viewModel.fetchEvent(FilterFragmentEvent.UpdateFilter(state.filter))
                        }

                        FilterFragmentState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.scrollView.visibility = View.GONE
                        }

                        is FilterFragmentState.FilterState -> {
                            updateCountries(state.filter)
                            updateGenreList(state.filter)
                            updateSortBy(state.filter)
                            updateTabLayout(state.filter)
                            setApplyButton(state.filter)
                        }
                    }

                }
        }
    }

    private fun setTabLayout(filter: Filter) {
        val position = MovieType.entries.indexOf(filter.type)
        binding.typeChooser.selectTab(binding.typeChooser.getTabAt(position))
    }

    private fun updateGenreList(filter: Filter) {
        val items = genreListAdapter.currentList
        val newItems =
            items.map { item -> item.copy(isChecked = filter.genres.contains(item.genre)) }
        genreListAdapter.onItemClickListener = { genre, isChecked ->
            val newList = filter.genres.toMutableList().apply {
                if (isChecked) add(genre)
                else remove(genre)
            }
            viewModel.fetchEvent(FilterFragmentEvent.UpdateFilter(filter.copy(genres = newList)))
        }
        genreListAdapter.submitList(newItems)
    }

    private fun updateCountries(filter: Filter) {
        val items = binding.countrySpinner.popUpAdapter?.items
        items?.let {
            val newItems =
                it.map { item -> item.copy(isChecked = filter.countries.contains(item.country)) }
            binding.countrySpinner.updateItems(newItems)
        }
        binding.countrySpinner.popUpAdapter?.onItemCheckBoxChanged = { country, isChecked ->
            val newList = filter.countries.toMutableList().apply {
                if (isChecked) add(country)
                else remove(country)
            }
            viewModel.fetchEvent(FilterFragmentEvent.UpdateFilter(filter.copy(countries = newList)))
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

    private fun updateSortBy(filter: Filter) {
        val sortViewList =
            listOf(binding.sortByPopularity, binding.sortByLatest, binding.sortByRating)
        sortViewList.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.fetchEvent(FilterFragmentEvent.UpdateFilter(filter.copy(sortType = SortType.entries[index])))
            }
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

    private fun updateTabLayout(filter: Filter) {
        binding.typeChooser.setOnTabClickListener { position ->
            viewModel.fetchEvent(
                FilterFragmentEvent.UpdateFilter(
                    filter.copy(
                        type = MovieType.entries[position]
                    )
                )
            )
        }
    }

    private fun setClickListeners() {
        binding.buttonBack.setOnClickListener { navigation.navigateUp() }
        binding.buttonReset.setOnClickListener {
            viewModel.fetchEvent(FilterFragmentEvent.Reset)
            navigation.navigateUp()
        }
    }

    private fun setApplyButton(filter: Filter) {
        binding.applyButton.setOnClickListener {
            viewModel.fetchEvent(
                FilterFragmentEvent.SaveFilter(
                    filter
                )
            )
            navigation.navigateUp()
        }
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

    private fun setGenreList(filter: Filter, genreList: List<String>) {
        val genres = genreList.map {
            GenreItem(it, filter.genres.contains(it))
        }
        binding.genreRecyclerView.adapter = genreListAdapter
        genreListAdapter.submitList(genres)
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
        binding.rangeYearSlider.values[0] = filter.yearFrom.toFloat()
        binding.rangeYearSlider.values[1] = filter.yearTo.toFloat()
        binding.rangeRatingSlider.values[0] = filter.ratingFrom.toFloat()
        binding.rangeRatingSlider.values[1] = filter.ratingTo.toFloat()
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