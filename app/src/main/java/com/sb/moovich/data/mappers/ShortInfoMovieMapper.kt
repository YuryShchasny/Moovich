package com.sb.moovich.data.mappers

import com.sb.moovich.data.model.MoviePoster
import com.sb.moovich.data.model.MovieRating
import com.sb.moovich.data.model.ShortMovieInfoDto
import com.sb.moovich.domain.entity.ShortMovieInfo
import javax.inject.Inject

class ShortInfoMovieMapper @Inject constructor() {

    fun mapDtoToEntity(shortMovieInfoDto: ShortMovieInfoDto): ShortMovieInfo = ShortMovieInfo(
        id = shortMovieInfoDto.id ?: throw NullPointerException(),
        name = shortMovieInfoDto.name ?: SOMETHING_WENT_WRONG,
        rating = shortMovieInfoDto.rating?.kinopoisk ?: shortMovieInfoDto.rating?.imdb ?: 0.0,
        previewUrl = shortMovieInfoDto.poster?.previewUrl ?: ""
    )

    fun mapEntityToDto(shortInfoMovie: ShortMovieInfo): ShortMovieInfoDto = ShortMovieInfoDto(
        id = shortInfoMovie.id,
        name = shortInfoMovie.name,
        rating = MovieRating(shortInfoMovie.rating),
        poster = MoviePoster(shortInfoMovie.previewUrl)
    )

    fun mapListDtoToListEntity(shortMovieInfoDto: List<ShortMovieInfoDto>) = shortMovieInfoDto.map {
        mapDtoToEntity(it)
    }
}