package com.sb.moovich.di

import android.app.Application

class MoovichApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}