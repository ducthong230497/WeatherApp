package com.weather.app.presenter.weather_detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.airbnb.epoxy.EpoxyModel
import com.weather.app.R
import com.weather.app.databinding.ActivityWeatherDetailBinding
import com.weather.app.domain.entities.DataResult
import com.weather.app.presenter.weather_detail.epoxy.CurrentWeatherEpoxyModel_
import com.weather.app.presenter.weather_detail.epoxy.ForecastWeatherEpoxyModel_
import com.weather.app.utils.zipLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WeatherDetailActivity : AppCompatActivity() {
    companion object {
        private const val CITY_ID = "CITY_ID"
        fun startActivity(activity: Activity, cityId: Long) {
            Intent(activity, WeatherDetailActivity::class.java).apply {
                putExtra(CITY_ID, cityId)
                activity.startActivity(this)
            }
        }
    }

    private lateinit var binding: ActivityWeatherDetailBinding
    private val cityId by lazy { intent.getLongExtra(CITY_ID, 0) }
    private val viewModel: WeatherDetailViewModel by viewModel { parametersOf(cityId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViews()
        setUpViewModels()
    }

    private fun setUpViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_detail)
        binding.retry.setOnClickListener {
            viewModel.reload()
        }
        binding.refreshLayout.setOnRefreshListener { viewModel.reload() }
    }

    private fun setUpViewModels() {
        zipLiveData(
            viewModel.data,
            viewModel.isFavoriteCity
        ).observe(this) { (dataResult, isFavoriteCity) ->
            binding.refreshLayout.isRefreshing = false
            when (dataResult.status) {
                DataResult.Status.LOADING -> {
                    binding.loadingProgress.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.errorGroup.visibility = View.GONE
                }
                DataResult.Status.ERROR -> {
                    binding.loadingProgress.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.errorGroup.visibility = View.VISIBLE
                }
                DataResult.Status.SUCCESS -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            val data = dataResult.data
                            val current = data?.current
                            val daily = data?.daily
                            val city = viewModel.city
                            val models = ArrayList<EpoxyModel<*>>()
                            models.add(
                                CurrentWeatherEpoxyModel_()
                                    .id("CURRENT_WEATHER")
                                    .cityName(city?.name)
                                    .currentWeather(current)
                                    .isFavoriteCity(isFavoriteCity)
                                    .onFavoriteClick(View.OnClickListener {
                                        if (isFavoriteCity) {
                                            Toast.makeText(
                                                this@WeatherDetailActivity,
                                                getString(
                                                    R.string.removed_city_from_favorite,
                                                    city?.name
                                                ),
                                                Toast.LENGTH_LONG
                                            ).show()
                                            viewModel.removeFromFavorite(cityId)
                                        } else {
                                            Toast.makeText(
                                                this@WeatherDetailActivity,
                                                getString(
                                                    R.string.added_city_to_favorite,
                                                    city?.name
                                                ),
                                                Toast.LENGTH_LONG
                                            ).show()
                                            viewModel.addFromFavorite(cityId)
                                        }
                                    })
                            )
                            daily?.slice(IntRange(1, 3))?.forEachIndexed { index, forecastWeather ->
                                models.add(
                                    ForecastWeatherEpoxyModel_()
                                        .id("FORECAST_WEATHER_$index")
                                        .forecastWeather(forecastWeather)
                                )
                            }

                            withContext(Dispatchers.Main) {
                                binding.loadingProgress.visibility = View.GONE
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.errorGroup.visibility = View.GONE
                                binding.recyclerView.setModels(models)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }
}
