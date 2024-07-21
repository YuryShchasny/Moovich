package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("name")
    val name: String?,
)