package com.weather.app.domain.use_cases

import com.weather.app.domain.entities.City
import com.weather.app.domain.repositories.CityRepository

interface SearchCityUseCase {
    suspend fun execute(name: String): List<City>
}

class SearchCityUseCaseImpl(
    private val cityRepository: CityRepository
): SearchCityUseCase {

    override suspend fun execute(name: String): List<City> {
        return cityRepository.searchCity(name)
    }
}
