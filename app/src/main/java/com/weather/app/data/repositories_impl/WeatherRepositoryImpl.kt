package com.weather.app.data.repositories_impl

import com.weather.app.data.database.*
import com.weather.app.data.database.daos.WeatherDao
import com.weather.app.data.network.Apis
import com.weather.app.domain.entities.*
import com.weather.app.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.*
import java.lang.Exception

class WeatherRepositoryImpl(
    private val apis: Apis,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun fetchWeatherInfo(
        cityId: Long,
        lat: Float,
        lon: Float
    ): Flow<DataResult<WeatherInfo?>> {
        return flow {
            emit(DataResult.loading())
            val response = apis.getWeatherInfo(lat, lon)
            if (response.isSuccessful) {
                emit(DataResult.success(response.body()?.copy(cityId = cityId)))
            } else {
                emit(DataResult.error(Exception(response.message())))
            }
        }
    }

    override suspend fun getWeatherInfo(cityId: Long): Flow<DataResult<WeatherInfo?>> {
        return flow {
            emit(DataResult.loading())
            emitAll(
                weatherDao.getWeatherAtCity(cityId).mapNotNull {
                    if (it == null) return@mapNotNull null
                    DataResult.success(
                        WeatherInfo(
                            it.cityId,
                            CurrentWeather(
                                it.current?.temp,
                                it.current?.humidity,
                                it.current?.windSpeed,
                                it.current?.windDeg,
                                it.current?.windGust,
                                it.current?.weather?.map { Weather(it.id, it.main, it.description) }
                            ),
                            it.daily?.map {
                                ForecastWeather(
                                    it.dt,
                                    ForecastTemperature(
                                        it.temp?.day,
                                        it.temp?.min,
                                        it.temp?.max,
                                        it.temp?.night,
                                        it.temp?.eve,
                                        it.temp?.morn
                                    ),
                                    it.humidity,
                                    it.windSpeed,
                                    it.windDeg,
                                    it.windGust,
                                    it.weather?.map { Weather(it.id, it.main, it.description) }
                                )
                            }
                        )
                    )
                }
            )
        }
    }

    override suspend fun cacheWeatherInfo(weatherInfo: WeatherInfo): Int {
        return weatherDao.upsert(WeatherInfoEntity(
            0,
            weatherInfo.cityId,
            CurrentWeatherEntity(
                weatherInfo.current?.temp,
                weatherInfo.current?.humidity,
                weatherInfo.current?.windSpeed,
                weatherInfo.current?.windDeg,
                weatherInfo.current?.windGust,
                weatherInfo.current?.weather?.map { WeatherEntity(it.id, it.main, it.description) },
            ),
            weatherInfo.daily?.map {
                ForecastWeatherEntity(
                    it.dt,
                    ForecastTemperatureEntity(
                        it.temp?.day,
                        it.temp?.min,
                        it.temp?.max,
                        it.temp?.night,
                        it.temp?.eve,
                        it.temp?.morn,
                    ),
                    it.humidity,
                    it.windSpeed,
                    it.windDeg,
                    it.windGust,
                    it.weather?.map { WeatherEntity(it.id, it.main, it.description) },
                )
            }
        ))
    }
}
