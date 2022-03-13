package com.weather.app._di

import com.google.gson.Gson
import com.weather.app.BuildConfig
import com.weather.app.data.database.AppDatabase
import com.weather.app.data.network.Apis
import com.weather.app.data.repositories_impl.CityRepositoryImpl
import com.weather.app.data.repositories_impl.WeatherRepositoryImpl
import com.weather.app.domain.repositories.CityRepository
import com.weather.app.domain.repositories.WeatherRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { Gson() }

    single {
        val timeoutTime = 1L
        val builder = OkHttpClient.Builder()
            .readTimeout(timeoutTime, TimeUnit.MINUTES)
            .connectTimeout(timeoutTime, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder.build()
    }

    single {
        val baseURL = BuildConfig.WEATHER_API_URL
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(Apis::class.java) }
}

val databaseModule = module {
    single(createdAtStart = true) { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().cityDao() }
    single { get<AppDatabase>().weatherDao() }
    single { get<AppDatabase>().favoriteCityDao() }
}

val repositoryModule = module {
    single<CityRepository> { CityRepositoryImpl(cityDao = get(), favoriteCityDao = get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(apis = get(), weatherDao = get()) }
}
