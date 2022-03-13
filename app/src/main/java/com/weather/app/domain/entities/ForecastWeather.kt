package com.weather.app.domain.entities

import com.google.gson.annotations.SerializedName

data class ForecastWeather(
    @SerializedName("dt") val dt: Long?,
    @SerializedName("temp") val temp: ForecastTemperature?,
    @SerializedName("humidity") val humidity: Float?,
    @SerializedName("wind_speed") val windSpeed: Float?,
    @SerializedName("wind_deg") val windDeg: Float?,
    @SerializedName("wind_gust") val windGust: Float?,
    @SerializedName("weather") val weather: List<Weather>?
)
