package com.sb.moovich.data.network.model

import com.google.gson.annotations.SerializedName

data class Backdrop(
    @SerializedName("url")
    val url: String?
)