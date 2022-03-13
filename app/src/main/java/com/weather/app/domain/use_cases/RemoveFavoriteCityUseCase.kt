package com.weather.app.domain.use_cases

import com.weather.app.domain.repositories.CityRepository

interface RemoveFavoriteCityUseCase {
    suspend fun execute(cityId: Long): Int
}

class RemoveFavoriteCityUseCaseImpl(
    private val cityRepository: CityRepository
): RemoveFavoriteCityUseCase {
    override suspend fun execute(cityId: Long): Int {
        return cityRepository.removeFromFavorite(cityId)
    }
}
