package com.weather.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.weather.app.app.Constants
import com.weather.app.data.database.converters.ListTypeConverters
import com.weather.app.data.database.converters.ObjectTypeConverters
import com.weather.app.data.database.daos.CityDao
import com.weather.app.data.database.daos.FavoriteCityDao
import com.weather.app.data.database.daos.WeatherDao

@Database(
    entities = [
        CityEntity::class,
        WeatherInfoEntity::class,
        FavoriteCityEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ObjectTypeConverters::class,
    ListTypeConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
    abstract fun favoriteCityDao(): FavoriteCityDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val currentInstance = INSTANCE
            if (currentInstance != null) {
                return currentInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).createFromAsset(
                    "database/app_database.db",
                    object : RoomDatabase.PrepackagedDatabaseCallback() {
                        override fun onOpenPrepackagedDatabase(db: SupportSQLiteDatabase) {
                            super.onOpenPrepackagedDatabase(db)
                            val sharedPreferences = context.getSharedPreferences(
                                Constants.APP_SHARED_PREFERENCES,
                                Context.MODE_PRIVATE
                            )
                            sharedPreferences.edit().putBoolean(Constants.PRE_POPULATE_DB, true)
                                .apply()
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

