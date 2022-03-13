package com.weather.app.data.database

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.weather.app.domain.entities.ForecastTemperature

@Entity(
    tableName = "WeatherInfo",
    indices = [
        Index("id", unique = true),
        Index("cityId", unique = true),
    ],
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("cityId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WeatherInfoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "cityId") val cityId: Long,
    @ColumnInfo(name = "current") val current: CurrentWeatherEntity?,
    @ColumnInfo(name = "daily") val daily: List<ForecastWeatherEntity>?
)

data class CurrentWeatherEntity(
    @SerializedName("temp") val temp: Float?,
    @SerializedName("humidity") val humidity: Float?,
    @SerializedName("wind_speed") val windSpeed: Float?,
    @SerializedName("wind_deg") val windDeg: Float?,
    @SerializedName("wind_gust") val windGust: Float?,
    @SerializedName("weather") val weather: List<WeatherEntity>?
)

data class ForecastWeatherEntity(
    @SerializedName("dt") val dt: Long?,
    @SerializedName("temp") val temp: ForecastTemperatureEntity?,
    @SerializedName("humidity") val humidity: Float?,
    @SerializedName("wind_speed") val windSpeed: Float?,
    @SerializedName("wind_deg") val windDeg: Float?,
    @SerializedName("wind_gust") val windGust: Float?,
    @SerializedName("weather") val weather: List<WeatherEntity>?
)

data class ForecastTemperatureEntity(
    @SerializedName("day") val day: Float?,
    @SerializedName("min") val min: Float?,
    @SerializedName("max") val max: Float?,
    @SerializedName("night") val night: Float?,
    @SerializedName("eve") val eve: Float?,
    @SerializedName("morn") val morn: Float?,
)

data class WeatherEntity(
    @SerializedName("id") val id: Long?,
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?,
)
