package com.sb.moovich.domain.entity

data class Filter(
    val type: MovieType = MovieType.ALL,
    val sortType: SortType = SortType.POPULARITY,
    val genres: List<String> = listOf(),
    val yearFrom: Int = 1937,
    val yearTo: Int = 2024,
    val ratingFrom: Int = 0,
    val ratingTo: Int = 10,
    val countries: List<String> = listOf()
) {
    fun getFiltersCount(): Int {
        val plusYears = 1
        val plusRating = 1
        val plusType = if(type != MovieType.ALL) 1 else 0
        return genres.size + plusYears + plusRating + countries.size + plusType
    }
}