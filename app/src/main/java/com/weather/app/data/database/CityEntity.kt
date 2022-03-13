package com.weather.app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "City", indices = [Index("id", unique = true), Index("name", unique = true)]
)
data class CityEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "coord") val coord: CoordEntity?,
) {
}

data class CoordEntity(
    @SerializedName("lat") val lat: Float,
    @SerializedName("lon") val lon: Float
)
