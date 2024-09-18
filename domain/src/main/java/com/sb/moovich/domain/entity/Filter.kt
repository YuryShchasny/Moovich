package com.sb.moovich.domain.entity

data class Filter(
    val type: MovieType = MovieType.ALL,
    val sortType: SortType = SortType.POPULARITY,
    val genres: List<String> = listOf(),
    val yearFrom: Int = YEAR_FROM,
    val yearTo: Int = YEAR_TO,
    val ratingFrom: Int = 0,
    val ratingTo: Int = 10,
    val countries: List<String> = listOf()
) {
    companion object {
        private const val YEAR_FROM = 1937
        private const val YEAR_TO = 2024
    }
    fun getFiltersCount(): Int {
        val plusYears = if(yearFrom == YEAR_FROM && yearTo == YEAR_TO) 0 else 1
        val plusRating = if(ratingFrom == 0 && ratingTo == 10) 0 else 1
        val plusType = if(type != MovieType.ALL) 1 else 0
        val plusSortType = if (sortType != SortType.POPULARITY) 1 else 0
        return genres.size + plusYears + plusRating + countries.size + plusType + plusSortType
    }

    fun hasFilters() = getFiltersCount() != 0
}