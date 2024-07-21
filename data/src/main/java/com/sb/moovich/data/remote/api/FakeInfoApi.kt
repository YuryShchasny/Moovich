package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.CountryDto
import com.sb.moovich.data.remote.dto.GenreDto
import retrofit2.Response

class FakeInfoApi: InfoApi {
    override fun getGenres(): Response<List<GenreDto>> {
        return Response.success(
            listOf(
                GenreDto("Family"),
                GenreDto("Action"),
                GenreDto("Fantasy"),
                GenreDto("Adventure"),
                GenreDto("Fantastic"),
                GenreDto("Anime"),
                GenreDto("History"),
                GenreDto("Horror"),
            )
        )
    }

    override fun getCountries(): Response<List<CountryDto>> {
        return Response.success(
            listOf(
                CountryDto("USA"),
                CountryDto("Russia"),
                CountryDto("Ukraine"),
                CountryDto("Belarus"),
                CountryDto("Poland"),
                CountryDto("Italy"),
                CountryDto(".USA"),
                CountryDto(".Russia"),
                CountryDto(".Ukraine"),
                CountryDto(".Belarus"),
                CountryDto(".Poland"),
                CountryDto(".Italy"),
                CountryDto("..USA"),
                CountryDto("..Russia"),
                CountryDto("..Ukraine"),
                CountryDto("..Belarus"),
                CountryDto("..Poland"),
                CountryDto("..Italy"),
            )
        )
    }
}