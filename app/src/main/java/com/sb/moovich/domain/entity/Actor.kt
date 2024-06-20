package com.sb.moovich.domain.entity

import java.io.Serializable

data class Actor(
    val photo: String,
    val name: String,
    val description: String,
) : Serializable
