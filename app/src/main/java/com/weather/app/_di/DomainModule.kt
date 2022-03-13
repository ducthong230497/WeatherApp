package com.weather.app._di

import com.weather.app.domain.use_cases.*
import org.koin.dsl.module

val domainModule = module {
    factory<SearchCityUseCase> { SearchCityUseCaseImpl(cityRepository = get()) }
    factory<GetWeatherAtLocationUseCase> { GetWeatherAtLocationUseCaseImpl(weatherRepository = get(), cityRepository = get()) }
    factory<GetFavoriteCitiesUseCase> { GetFavoriteCitiesUseCaseImpl(citiRepository = get()) }
    factory<AddFavoriteCityUseCase> { AddFavoriteCityUseCaseImpl(cityRepository = get()) }
    factory<RemoveFavoriteCityUseCase> { RemoveFavoriteCityUseCaseImpl(cityRepository = get()) }
}
