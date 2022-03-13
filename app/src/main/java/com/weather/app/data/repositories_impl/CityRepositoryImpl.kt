package com.weather.app.data.repositories_impl

import com.weather.app.data.database.FavoriteCityEntity
import com.weather.app.data.database.daos.CityDao
import com.weather.app.data.database.daos.FavoriteCityDao
import com.weather.app.domain.entities.City
import com.weather.app.domain.entities.Coord
import com.weather.app.domain.repositories.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val favoriteCityDao: FavoriteCityDao
) : CityRepository {

    override suspend fun getCityId(cityId: Long): City {
        val result =  cityDao.getCityFromId(cityId)
        return City(result.id, result.name, result.country, Coord(result.coord?.lat ?: 0f, result.coord?.lon ?: 0f))
    }

    override suspend fun searchCity(name: String): List<City> {
        return cityDao.searchCity(name).map {
            City(it.id, it.name, it.country, Coord(it.coord?.lat ?: 0f, it.coord?.lon ?: 0f))
        }
    }

    override suspend fun getFavoriteCities(): Flow<List<City>?> {
        return cityDao.getFavoriteCities().map {
            it?.map {
                City(it.id, it.name, it.country, Coord(it.coord?.lat ?: 0f, it.coord?.lon ?: 0f))
            }
        }
    }

    override suspend fun isFavoriteCity(cityId: Long): Flow<Boolean> {
        return favoriteCityDao.isFavoriteCity(cityId).map { it != null }
    }

    override suspend fun addToFavorite(cityId: Long, addedAt: Long): Int {
        return favoriteCityDao.upsert(FavoriteCityEntity(cityId, addedAt))
    }

    override suspend fun removeFromFavorite(cityId: Long): Int {
        return favoriteCityDao.removeFromFavorite(cityId)
    }
}
