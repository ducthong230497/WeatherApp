package com.weather.app.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.weather.app.data.database.WeatherInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao: BaseDao<WeatherInfoEntity>() {
    @Query("SELECT * FROM WeatherInfo WHERE cityId = :cityId")
    abstract fun getWeatherAtCity(cityId: Long): Flow<WeatherInfoEntity?>
}
