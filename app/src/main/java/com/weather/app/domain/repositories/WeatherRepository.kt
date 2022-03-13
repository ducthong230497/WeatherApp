package com.weather.app.domain.repositories

import com.weather.app.domain.entities.DataResult
import com.weather.app.domain.entities.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchWeatherInfo(cityId: Long, lat: Float, lon: Float): Flow<DataResult<WeatherInfo?>>
    suspend fun getWeatherInfo(cityId: Long): Flow<DataResult<WeatherInfo?>>
    suspend fun cacheWeatherInfo(weatherInfo: WeatherInfo): Int
}
