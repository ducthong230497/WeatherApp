package com.weather.app.presenter.weather_detail.epoxy

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.weather.app.R
import com.weather.app.domain.entities.CurrentWeather

@EpoxyModelClass(layout = R.layout.layout_current_weather_item)
abstract class CurrentWeatherEpoxyModel: EpoxyModelWithHolder<CurrentWeatherEpoxyModel.ViewHolder>() {

    @EpoxyAttribute
    var cityName: String? = null

    @EpoxyAttribute
    @JvmField
    var isFavoriteCity: Boolean = false

    @EpoxyAttribute
    var currentWeather: CurrentWeather? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onFavoriteClick: View.OnClickListener? = null

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        val context = holder.currentTemp.context
        holder.currentWeather.text = context.getString(R.string.current_weather_at, cityName)
        holder.currentTemp.text = context.getString(R.string.temperature, currentWeather?.temp ?: 0f)
        holder.currentHumidity.text = context.getString(R.string.humidity, currentWeather?.humidity ?: 0f)
        holder.currentWindSpeed.text = context.getString(R.string.wind_speed, currentWeather?.windSpeed ?: 0f)
        holder.currentWindDeg.text = context.getString(R.string.wind_deg, currentWeather?.windDeg ?: 0f)
        holder.currentWindGust.text = context.getString(R.string.wind_gust, currentWeather?.windGust ?: 0f)
        holder.weatherDescription.text = context.getString(R.string.weather, currentWeather?.weather?.firstOrNull()?.description ?: "")

        if (isFavoriteCity) {
            holder.btnFavorite.text = context.getString(R.string.remove_city_from_favorite, cityName)
        } else {
            holder.btnFavorite.text = context.getString(R.string.add_city_to_favorite, cityName)
        }
        holder.btnFavorite.setOnClickListener(onFavoriteClick)
    }

    class ViewHolder: EpoxyHolder() {
        lateinit var currentWeather: AppCompatTextView
        lateinit var currentTemp: AppCompatTextView
        lateinit var currentHumidity: AppCompatTextView
        lateinit var currentWindSpeed: AppCompatTextView
        lateinit var currentWindDeg: AppCompatTextView
        lateinit var currentWindGust: AppCompatTextView
        lateinit var weatherDescription: AppCompatTextView
        lateinit var btnFavorite: AppCompatButton
        override fun bindView(itemView: View) {
            currentWeather = itemView.findViewById(R.id.currentWeather)
            currentTemp = itemView.findViewById(R.id.currentTemp)
            currentHumidity = itemView.findViewById(R.id.currentHumidity)
            currentWindSpeed = itemView.findViewById(R.id.currentWindSpeed)
            currentWindDeg = itemView.findViewById(R.id.currentWindDeg)
            currentWindGust = itemView.findViewById(R.id.currentWindGust)
            weatherDescription = itemView.findViewById(R.id.weatherDescription)
            btnFavorite = itemView.findViewById(R.id.btnFavorite)
        }

    }
}
