package com.weather.app.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.weather.app.data.database.CoordEntity
import com.weather.app.data.database.CurrentWeatherEntity
import com.weather.app.data.database.ForecastTemperatureEntity

class ObjectTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromCurrentWeatherToString(data: CurrentWeatherEntity?): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromForecastTemperatureToString(data: ForecastTemperatureEntity?): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromStringToCurrentWeather(string: String?): CurrentWeatherEntity? {
        return gson.fromJson(string, CurrentWeatherEntity::class.java)
    }

    @TypeConverter
    fun fromStringToForecastTemperature(string: String?): ForecastTemperatureEntity? {
        return gson.fromJson(string, ForecastTemperatureEntity::class.java)
    }

    @TypeConverter
    fun fromCoordToString(data: CoordEntity?): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromStringToCoord(string: String?): CoordEntity? {
        return gson.fromJson(string, CoordEntity::class.java)
    }
}
