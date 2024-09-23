package com.sb.moovich.domain.entity

import java.io.Serializable

data class Collection(
    val slug: String,
    val name: String,
    val cover: String,
    val count: Int,
): Serializable
