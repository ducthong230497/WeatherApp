package com.weather.app.presenter.weather_detail

import androidx.lifecycle.*
import com.weather.app.domain.entities.City
import com.weather.app.domain.repositories.CityRepository
import com.weather.app.domain.use_cases.AddFavoriteCityUseCase
import com.weather.app.domain.use_cases.GetWeatherAtLocationUseCase
import com.weather.app.domain.use_cases.RemoveFavoriteCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherDetailViewModel(
    cityId: Long,
    getWeatherAtLocationUseCase: GetWeatherAtLocationUseCase,
    cityRepository: CityRepository,
    private val removeFavoriteCityUseCase: RemoveFavoriteCityUseCase,
    private val addFavoriteCityUseCase: AddFavoriteCityUseCase,
) : ViewModel() {

    var city: City? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            city = cityRepository.getCityId(cityId)
        }
    }

    private val _reload = MutableLiveData<Unit>().apply { value = Unit }
    val data = Transformations.switchMap(_reload) {
        getWeatherAtLocationUseCase.execute(cityId).asLiveData(viewModelScope.coroutineContext)
    }
    val isFavoriteCity = liveData(Dispatchers.IO) {
        emitSource(
            cityRepository.isFavoriteCity(cityId).asLiveData(viewModelScope.coroutineContext)
        )
    }

    fun reload() {
        _reload.value = Unit
    }

    fun addFromFavorite(cityId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavoriteCityUseCase.execute(cityId)
        }
    }

    fun removeFromFavorite(cityId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavoriteCityUseCase.execute(cityId)
        }
    }
}
