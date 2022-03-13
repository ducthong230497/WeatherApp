package com.weather.app.data.network

import com.weather.app.BuildConfig
import com.weather.app.domain.entities.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {
    @GET("/data/2.5/onecall")
    suspend fun getWeatherInfo(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
    ): Response<WeatherInfo>
}
