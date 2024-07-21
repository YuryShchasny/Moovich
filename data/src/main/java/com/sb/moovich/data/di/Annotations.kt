package com.sb.moovich.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FakeMovieApiProvide

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieApiProvide

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class InfoApiProvide

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FakeInfoApiProvide