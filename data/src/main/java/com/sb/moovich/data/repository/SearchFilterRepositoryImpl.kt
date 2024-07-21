package com.sb.moovich.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.sb.moovich.data.di.FakeInfoApiProvide
import com.sb.moovich.data.remote.api.InfoApi
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.repository.SearchFilterRepository
import javax.inject.Inject

class SearchFilterRepositoryImpl @Inject constructor(
    @FakeInfoApiProvide private val infoApi: InfoApi,
    private val sharedPreferences: SharedPreferences
): SearchFilterRepository {
    companion object {
        private const val ARG_FILTER = "arg_filter"
    }
    private val gson = Gson()
    override fun getFilter(): Filter {
        val filter = sharedPreferences.getString(ARG_FILTER, null)
        return filter?.let {
            gson.fromJson(filter, Filter::class.java)
        } ?: Filter()
    }

    override fun saveFilter(filter: Filter) {
        val json = gson.toJson(filter)
        sharedPreferences.edit().putString(ARG_FILTER, json).apply()
    }

    override suspend fun getGenres(): List<String> {
        val response = infoApi.getGenres()
        if (response.isSuccessful) {
            response.body()?.let { genres ->
                return genres.map { it.name ?: ""}.filter { it.isNotEmpty() }
            }
        }
        return emptyList()
    }

    override suspend fun getCountries(): List<String> {
        val response = infoApi.getCountries()
        if (response.isSuccessful) {
            response.body()?.let { countries ->
                return countries.map { it.name ?: ""}.filter { it.isNotEmpty() }
            }
        }
        return emptyList()
    }
}