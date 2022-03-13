package com.weather.app.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weather.app.data.database.CurrentWeatherEntity
import com.weather.app.data.database.ForecastTemperatureEntity
import com.weather.app.data.database.ForecastWeatherEntity
import com.weather.app.data.database.WeatherEntity
import java.lang.reflect.Type

class ListTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromListForecastWeatherToString(data: List<ForecastWeatherEntity>?): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromListWeatherToString(data: List<WeatherEntity>?): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromStringToListForecastWeather(string: String?): List<ForecastWeatherEntity>? {
        val listType: Type = object : TypeToken<List<ForecastWeatherEntity>>() {}.type
        return gson.fromJson(string, listType)
    }

    @TypeConverter
    fun fromStringToListWeather(string: String?): List<WeatherEntity>? {
        val listType: Type = object : TypeToken<List<WeatherEntity>>() {}.type
        return gson.fromJson(string, listType)
    }
}
