package com.sb.moovich.data.mappers

import com.sb.moovich.data.model.MovieInfoDto
import com.sb.moovich.data.model.Person
import com.sb.moovich.domain.entity.Actor
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import javax.inject.Inject

const val SOMETHING_WENT_WRONG = "Something went wrong"

class MovieMapper @Inject constructor() {
    companion object {
        private const val KINOPOISK_URL = "https://www.kinopoisk.ru/film/"
    }

    fun mapDtoToEntity(movieInfoDto: MovieInfoDto): MovieInfo = MovieInfo(
        id = movieInfoDto.id ?: throw NullPointerException(),
        name = movieInfoDto.name ?: SOMETHING_WENT_WRONG,
        description = movieInfoDto.description ?: "",
        rating = movieInfoDto.rating?.kinopoisk ?: movieInfoDto.rating?.imdb ?: 0.0,
        poster = movieInfoDto.poster?.previewUrl ?: "",
        backdrop = movieInfoDto.backdrop?.url ?: "",
        movieLength = movieInfoDto.movieLength ?: 0,
        urlWatch = "$KINOPOISK_URL${movieInfoDto.id}",
        year = movieInfoDto.year ?: 0,
        genres = movieInfoDto.genres?.map { genre ->
            genre.name?.let { name ->
                name.replaceFirstChar { it.uppercaseChar() }
            }
        } ?: listOf(),
        actors = mapPersonsToActors(movieInfoDto.persons),
        similarMovies = movieInfoDto.similarMovies?.map {
            ShortMovieInfo(
                it.id ?: throw NullPointerException(),
                it.name ?: SOMETHING_WENT_WRONG,
                it.rating?.kinopoisk ?: it.rating?.imdb ?: 0.0,
                it.poster?.previewUrl ?: ""
            )
        } ?: listOf()

    )

    private fun mapPersonsToActors(persons: List<Person>?): List<Actor> {
        return persons?.let { list ->
            list.filter { it.enProfession == "actor" && it.photo != null && it.name != null && it.description != null }
            list.map {
                Actor(it.photo ?:"", it.name ?: "", it.description ?:"")
            }
        } ?: listOf()

    }

}