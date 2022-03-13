package com.weather.app._di

import com.weather.app.presenter.main.MainViewModel
import com.weather.app.presenter.weather_detail.WeatherDetailViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module {
    viewModel {
        MainViewModel(
            androidApplication(),
            searchCityUseCase = get(),
            getFavoriteCitiesUseCase = get(),
            removeFavoriteCityUseCase = get()
        )
    }
    viewModel { (cityId: Long) ->
        WeatherDetailViewModel(
            cityId = cityId,
            getWeatherAtLocationUseCase = get(),
            cityRepository = get(),
            removeFavoriteCityUseCase = get(),
            addFavoriteCityUseCase = get()
        )
    }
}
