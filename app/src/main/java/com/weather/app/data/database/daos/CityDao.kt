package com.weather.app.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.weather.app.data.database.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CityDao: BaseDao<CityEntity>() {
    @Query("SELECT * FROM City WHERE id = :cityId")
    abstract fun getCityFromId(cityId: Long): CityEntity

    @Query("SELECT * FROM City WHERE name LIKE '%' || :name || '%'")
    abstract suspend fun searchCity(name: String): List<CityEntity>

    @Query("SELECT c.id, c.name, c.country, c.coord FROM City AS c JOIN FavoriteCity AS f ON c.id = f.id ORDER BY f.addedAt DESC")
    abstract fun getFavoriteCities(): Flow<List<CityEntity>?>
}
