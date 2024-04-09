package com.sb.moovich.data.mappers

import com.sb.moovich.data.model.Person
import com.sb.moovich.data.model.MovieInfoDto
import com.sb.moovich.domain.entity.Actor
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    companion object {
        private const val KINOPOISK_URL = "https://www.kinopoisk.ru/film/"
    }

    fun mapDtoToEntity(movieInfoDto: MovieInfoDto): MovieInfo = MovieInfo(
        id = movieInfoDto.id,
        name = movieInfoDto.name,
        description = movieInfoDto.description,
        rating = movieInfoDto.rating.kinopoisk,
        backdrop = movieInfoDto.backdrop.url,
        movieLength = movieInfoDto.movieLength,
        urlWatch = "$KINOPOISK_URL${movieInfoDto.id}",
        year = movieInfoDto.year,
        genres = movieInfoDto.genres.map { genre -> genre.name.replaceFirstChar { it.uppercaseChar() } },
        actors = mapPersonsToActors(movieInfoDto.persons),
        similarMovies = movieInfoDto.similarMovies?.map {
            ShortMovieInfo(
                it.id,
                it.name,
                it.rating?.kinopoisk ?: 0.0,
                it.poster.previewUrl
            )
        } ?: listOf()

    )
    private fun mapPersonsToActors(persons: List<Person>): List<Actor> {
        return persons
            .filter { it.enProfession == "actor" && it.photo != null && it.name != null && it.description != null}
            .map { Actor(it.photo, it.name, it.description) }
    }

}