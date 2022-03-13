package com.weather.app.domain.use_cases

import com.weather.app.domain.entities.DataResult
import com.weather.app.domain.entities.WeatherInfo
import com.weather.app.domain.repositories.CityRepository
import com.weather.app.domain.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface GetWeatherAtLocationUseCase {
    fun execute(cityId: Long): Flow<DataResult<WeatherInfo?>>
}

class GetWeatherAtLocationUseCaseImpl(
    private val weatherRepository: WeatherRepository,
    private val cityRepository: CityRepository
): GetWeatherAtLocationUseCase {
    override fun execute(cityId: Long): Flow<DataResult<WeatherInfo?>> {
        return flow {
            val city = cityRepository.getCityId(cityId)
            emitAll(
                combine(weatherRepository.getWeatherInfo(cityId), weatherRepository.fetchWeatherInfo(cityId,city.coord?.lat ?: 0f, city.coord?.lon ?: 0f)) { cache, netWork ->
                    return@combine when (netWork.status) {
                        DataResult.Status.LOADING -> cache
                        DataResult.Status.ERROR -> netWork
                        DataResult.Status.SUCCESS -> {
                            if (netWork.data != null && netWork.data.hashCode() != cache.data?.hashCode()) {
                                weatherRepository.cacheWeatherInfo(netWork.data)
                            }
                            cache
                        }
                    }
                }
            )
        }.flowOn(Dispatchers.IO).distinctUntilChanged()
    }

}
