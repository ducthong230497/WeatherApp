package com.weather.app.domain.repositories

import com.weather.app.domain.entities.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun getCityId(cityId: Long): City
    suspend fun searchCity(name: String): List<City>
    suspend fun getFavoriteCities(): Flow<List<City>?>
    suspend fun isFavoriteCity(cityId: Long): Flow<Boolean>
    suspend fun addToFavorite(cityId: Long, addedAt: Long): Int
    suspend fun removeFromFavorite(cityId: Long): Int
}
