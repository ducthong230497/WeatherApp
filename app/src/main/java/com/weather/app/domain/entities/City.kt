package com.weather.app.domain.entities

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("coord") val coord: Coord?,
)

data class Coord(
    @SerializedName("lat") val lat: Float,
    @SerializedName("lon") val lon: Float
)
