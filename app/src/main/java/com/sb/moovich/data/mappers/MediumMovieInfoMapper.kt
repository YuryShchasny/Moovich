package com.sb.moovich.data.mappers

import com.sb.moovich.data.network.model.MediumMovieInfoDto
import com.sb.moovich.domain.entity.MediumMovieInfo
import javax.inject.Inject

class MediumMovieInfoMapper @Inject constructor(){
    private fun mapDtoToEntity(mediumMovieInfoDto: MediumMovieInfoDto): MediumMovieInfo = MediumMovieInfo(
        id = mediumMovieInfoDto.id ?: throw NullPointerException(),
        name = getName(mediumMovieInfoDto),
        description = mediumMovieInfoDto.description ?: "",
        rating = mediumMovieInfoDto.rating?.kinopoisk ?: mediumMovieInfoDto.rating?.imdb ?: 0.0,
        poster = mediumMovieInfoDto.poster?.previewUrl ?: "",
        movieLength = mediumMovieInfoDto.movieLength ?: 0,
        year = mediumMovieInfoDto.year ?: 0,
        genres = mediumMovieInfoDto.genres?.map { genre ->
            genre.name?.let { name ->
                name.replaceFirstChar { it.uppercaseChar() }
            }
        } ?: listOf(),
    )
    fun mapListDtoToListEntity(mediumMovieInfoDtoList: List<MediumMovieInfoDto>) = mediumMovieInfoDtoList.map {
        mapDtoToEntity(it)
    }
    private fun getName(mediumMovieInfoDto: MediumMovieInfoDto) : String {
        mediumMovieInfoDto.apply {
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