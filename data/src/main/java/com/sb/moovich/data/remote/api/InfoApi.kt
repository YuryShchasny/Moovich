package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.CountryDto
import com.sb.moovich.data.remote.dto.GenreDto
import retrofit2.Response

interface InfoApi {
    companion object {
        private const val API_KEY = "R0V497J-ZGYMFXX-JJ9QSFS-96AN48G"
    }

    fun getGenres(): Response<List<GenreDto>>
    fun getCountries(): Response<List<CountryDto>>
}