package com.sb.moovich.data.mapper

import com.sb.moovich.core.exceptions.DataException
import com.sb.moovich.data.remote.dto.MovieDto
import com.sb.moovich.data.remote.dto.PersonDto
import com.sb.moovich.domain.entity.Actor
import com.sb.moovich.domain.entity.Movie
import javax.inject.Inject

class MovieDtoMapper @Inject constructor(): IDataMapper<MovieDto, Movie> {
    companion object {
        private const val KINOPOISK_URL = "https://www.kinopoisk.ru/film/"
    }

    override suspend fun mapDataToEntity(data: MovieDto): Movie = mapDataToEntity(data, true)

    override suspend fun mapEntityToData(entity: Movie): MovieDto {
        throw UnsupportedOperationException()
    }

    suspend fun mapDataToEntity(data: MovieDto, withSimilarMovies: Boolean) = Movie(
        id = data.id ?: throw DataException.NoMovieWithThisID(data.id),
        name = getName(data),
        description = data.description ?: "",
        rating = data.rating?.kinopoisk ?: data.rating?.imdb ?: 0.0,
        poster = getPoster(data),
        backdrop = data.backdrop?.url ?: getPoster(data),
        movieLength = data.movieLength ?: 0,
        urlWatch = "$KINOPOISK_URL${data.id}",
        year = data.year ?: 0,
        genres = data.genres?.mapNotNull { genre ->
            genre.name?.let { name ->
                name.replaceFirstChar { it.uppercaseChar() }
            }
        } ?: listOf(),
        actors = mapPersonsToActors(data.persons),
        similarMovies = if (withSimilarMovies) mapDataToEntityList(
            data.similarMovies ?: emptyList()
        )
        else emptyList()
    )

    suspend fun mapDataToEntityList(data: List<MovieDto>): List<Movie> = data.map { mapDataToEntity(it) }

    private fun mapPersonsToActors(persons: List<PersonDto>?): List<Actor> =
        persons?.let { list ->
            list.filter { it.enProfession == "actor" && it.photo != null && it.name != null && it.description != null }
            list.map {
                Actor(
                    id = it.id ?: throw DataException.NoActorWithThisID(it.id),
                    photo = it.photo,
                    name = it.name ?: "",
                    description = it.description ?: ""
                )
            }
        } ?: listOf()

    private fun getName(movieInfoDto: MovieDto): String {
        movieInfoDto.apply {
            name?.let {
                if (it.isNotEmpty()) {
                    return name
                }
            }
            alternativeName?.let {
                if (it.isNotEmpty()) {
                    return alternativeName
                }
            }
            return ""
        }
    }

    private fun getPoster(data: MovieDto) = data.poster?.previewUrl ?: data.poster?.url
}