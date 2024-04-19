package com.sb.moovich.data.mappers

import com.sb.moovich.data.network.model.MovieInfoDto
import com.sb.moovich.data.network.model.MoviePoster
import com.sb.moovich.data.network.model.MovieRating
import com.sb.moovich.data.network.model.ShortMovieInfoDto
import com.sb.moovich.domain.entity.ShortMovieInfo
import javax.inject.Inject

class ShortMovieInfoMapper @Inject constructor() {

    private fun mapDtoToEntity(shortMovieInfoDto: ShortMovieInfoDto): ShortMovieInfo = ShortMovieInfo(
        id = shortMovieInfoDto.id ?: throw NullPointerException(),
        name = getName(shortMovieInfoDto),
        rating = shortMovieInfoDto.rating?.kinopoisk ?: shortMovieInfoDto.rating?.imdb ?: 0.0,
        previewUrl = shortMovieInfoDto.poster?.previewUrl ?: "https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-logo/placeholder.svg"
    )

    fun mapListDtoToListEntity(shortMovieInfoDto: List<ShortMovieInfoDto>) = shortMovieInfoDto.map {
        mapDtoToEntity(it)
    }
    private fun getName(shortMovieInfoDto: ShortMovieInfoDto) : String {
        shortMovieInfoDto.apply {
            name?.let {
                if(it.isNotEmpty()) {
                    return name
                }
            }
            alternativeName?.let {
                if(it.isNotEmpty()) {
                    return alternativeName
                }
            }
            return SOMETHING_WENT_WRONG
        }
    }
}