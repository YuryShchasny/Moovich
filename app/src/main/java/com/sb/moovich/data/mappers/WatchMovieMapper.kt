package com.sb.moovich.data.mappers

import com.sb.moovich.data.database.model.WatchMovieDb
import com.sb.moovich.domain.entity.MediumMovieInfo
import javax.inject.Inject

class WatchMovieMapper @Inject constructor() {
    fun mapDbToEntity(watchMovieDb: WatchMovieDb): MediumMovieInfo = MediumMovieInfo(
        id = watchMovieDb.id,
        name = watchMovieDb.name,
        description = watchMovieDb.description,
        rating = watchMovieDb.rating,
        poster = watchMovieDb.poster,
        movieLength = watchMovieDb.movieLength,
        year = watchMovieDb.year,
        genres = watchMovieDb.genres
    )

    fun mapEntityToDb(mediumMovieInfo: MediumMovieInfo): WatchMovieDb = WatchMovieDb(
        id = mediumMovieInfo.id,
        name = mediumMovieInfo.name,
        description = mediumMovieInfo.description,
        rating = mediumMovieInfo.rating,
        poster = mediumMovieInfo.poster,
        movieLength = mediumMovieInfo.movieLength,
        year = mediumMovieInfo.year,
        genres = mediumMovieInfo.genres
    )

    fun mapListDbToListEntity(watchMovieDbList: List<WatchMovieDb>) = watchMovieDbList.map {
        mapDbToEntity(it)
    }
}