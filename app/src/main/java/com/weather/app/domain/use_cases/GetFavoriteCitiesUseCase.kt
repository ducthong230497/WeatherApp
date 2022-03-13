package com.weather.app.domain.use_cases

import com.weather.app.domain.entities.City
import com.weather.app.domain.repositories.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface GetFavoriteCitiesUseCase {
    fun execute(): Flow<List<City>?>
}

class GetFavoriteCitiesUseCaseImpl(
    private val citiRepository: CityRepository
): GetFavoriteCitiesUseCase {
    override fun execute(): Flow<List<City>?> {
        return flow { emitAll(citiRepository.getFavoriteCities()) }.flowOn(Dispatchers.IO).distinctUntilChanged()
    }
}
