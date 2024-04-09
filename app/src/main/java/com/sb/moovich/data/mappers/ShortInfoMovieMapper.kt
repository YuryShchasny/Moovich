package com.sb.moovich.data.mappers

import com.sb.moovich.data.model.ShortInfoMovieDto
import com.sb.moovich.domain.entity.ShortMovieInfo
import javax.inject.Inject

class ShortInfoMovieMapper @Inject constructor() {

    fun mapDtoToEntity(shortInfoMovieDto: ShortInfoMovieDto): ShortMovieInfo = ShortMovieInfo(
        id = shortInfoMovieDto.id,
        name = shortInfoMovieDto.name,
        rating = shortInfoMovieDto.rating?.kinopoisk ?: 0.0 ,
        previewUrl = shortInfoMovieDto.poster.previewUrl
    )

    fun mapListDtoToListEntity(shortInfoMovieDto: List<ShortInfoMovieDto>) = shortInfoMovieDto.map {
        mapDtoToEntity(it)
    }
}