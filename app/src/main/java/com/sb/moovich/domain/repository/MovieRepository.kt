package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getRecommendedMovies() : Flow<List<ShortMovieInfo>>
    fun getMovieById(id: Int) : Flow<MovieInfo>
}