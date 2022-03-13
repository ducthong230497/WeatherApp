package com.weather.app.domain.entities

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("cityId") val cityId: Long,
    @SerializedName("current") val current: CurrentWeather?,
    @SerializedName("daily") val daily: List<ForecastWeather>?
)
