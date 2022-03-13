package com.weather.app.domain.use_cases

import com.weather.app.domain.repositories.CityRepository

interface AddFavoriteCityUseCase {
    suspend fun execute(cityId: Long): Int
}

class AddFavoriteCityUseCaseImpl(
    private val cityRepository: CityRepository
): AddFavoriteCityUseCase {
    override suspend fun execute(cityId: Long): Int {
        return cityRepository.addToFavorite(cityId, System.currentTimeMillis())
    }
}
