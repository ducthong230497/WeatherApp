package com.weather.app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "FavoriteCity",
    primaryKeys = ["id", "addedAt"],
    indices = [
        Index("id", unique = true),
        Index("addedAt", unique = true),
    ]
)
data class FavoriteCityEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "addedAt") val addedAt: Long,
)
