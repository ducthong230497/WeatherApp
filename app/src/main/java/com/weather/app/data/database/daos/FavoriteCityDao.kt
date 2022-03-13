package com.weather.app.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.weather.app.data.database.FavoriteCityEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavoriteCityDao: BaseDao<FavoriteCityEntity>() {
    @Query("DELETE FROM FavoriteCity WHERE id = :cityId")
    abstract suspend fun removeFromFavorite(cityId: Long): Int

    @Query("SELECT * FROM FavoriteCity WHERE id = :cityId")
    abstract fun isFavoriteCity(cityId: Long): Flow<FavoriteCityEntity?>
}
