package com.sb.moovich.data.mappers

import com.sb.moovich.data.database.model.RecentMovieDb
import com.sb.moovich.domain.entity.MediumMovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import javax.inject.Inject

class RecentMovieMapper @Inject constructor() {
    fun mapDbToEntity(recentMovieDb: RecentMovieDb): MediumMovieInfo = MediumMovieInfo(
        id = recentMovieDb.id,
        name = recentMovieDb.name,
        description = recentMovieDb.description,
        rating = recentMovieDb.rating,
        poster = recentMovieDb.poster,
        movieLength = recentMovieDb.movieLength,
        year = recentMovieDb.year,
        genres = recentMovieDb.genres
    )

    fun mapEntityToDb(mediumMovieInfo: MediumMovieInfo): RecentMovieDb = RecentMovieDb(
        id = mediumMovieInfo.id,
        name = mediumMovieInfo.name,
        description = mediumMovieInfo.description,
        rating = mediumMovieInfo.rating,
        poster = mediumMovieInfo.poster,
        movieLength = mediumMovieInfo.movieLength,
        year = mediumMovieInfo.year,
        genres = mediumMovieInfo.genres,
        timestamp = System.currentTimeMillis()
    )

    fun mapListDbToListEntity(recentMovieDbList: List<RecentMovieDb>) = recentMovieDbList.map {
        mapDbToEntity(it)
    }
}