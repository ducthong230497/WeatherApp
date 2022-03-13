package com.weather.app.presenter.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.weather.app.app.Constants
import com.weather.app.domain.entities.City
import com.weather.app.domain.use_cases.GetFavoriteCitiesUseCase
import com.weather.app.domain.use_cases.RemoveFavoriteCityUseCase
import com.weather.app.domain.use_cases.SearchCityUseCase
import com.weather.app.utils.booleanFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val searchCityUseCase: SearchCityUseCase,
    getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
    private val removeFavoriteCityUseCase: RemoveFavoriteCityUseCase,
) : ViewModel() {

    private val _listCityResult = MutableLiveData<List<City>?>()
    val listCityResult: LiveData<List<City>?> = _listCityResult

    val prePopulateDb =
        application.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .booleanFlow(Constants.PRE_POPULATE_DB).asLiveData(viewModelScope.coroutineContext)

    val favoriteCities =
        getFavoriteCitiesUseCase.execute().asLiveData(viewModelScope.coroutineContext)

    fun searchCity(name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isNullOrEmpty()) {
                _listCityResult.postValue(null)
                return@launch
            }
            val result = searchCityUseCase.execute(name)
            _listCityResult.postValue(result)
        }
    }

    fun removeFromFavorite(cityId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavoriteCityUseCase.execute(cityId)
        }
    }
}
