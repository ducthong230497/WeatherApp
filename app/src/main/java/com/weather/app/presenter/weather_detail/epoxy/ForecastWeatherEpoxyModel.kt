package com.weather.app.presenter.weather_detail.epoxy

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.weather.app.R
import com.weather.app.domain.entities.ForecastWeather
import java.util.*

@EpoxyModelClass(layout = R.layout.layout_forecast_weather_item)
abstract class ForecastWeatherEpoxyModel: EpoxyModelWithHolder<ForecastWeatherEpoxyModel.ViewHolder>() {

    @EpoxyAttribute
    var forecastWeather: ForecastWeather? = null

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        val context = holder.forecastDate.context
        holder.forecastDate.text = context.getString(R.string.forecast_for_date, Date((forecastWeather?.dt ?: 0) * 1000).toString())
        holder.forecastTemp.text = context.getString(R.string.temperature, forecastWeather?.temp?.day ?: 0f)
        holder.forecastHumidity.text = context.getString(R.string.humidity, forecastWeather?.humidity ?: 0f)
        holder.forecastWindSpeed.text = context.getString(R.string.wind_speed, forecastWeather?.windSpeed ?: 0f)
        holder.forecastWindDeg.text = context.getString(R.string.wind_deg, forecastWeather?.windDeg ?: 0f)
        holder.forecastWindGust.text = context.getString(R.string.wind_gust, forecastWeather?.windGust ?: 0f)
    }

    class ViewHolder: EpoxyHolder() {
        lateinit var forecastDate: AppCompatTextView
        lateinit var forecastTemp: AppCompatTextView
        lateinit var forecastHumidity: AppCompatTextView
        lateinit var forecastWindSpeed: AppCompatTextView
        lateinit var forecastWindDeg: AppCompatTextView
        lateinit var forecastWindGust: AppCompatTextView
        override fun bindView(itemView: View) {
            forecastDate = itemView.findViewById(R.id.forecastDate)
            forecastTemp = itemView.findViewById(R.id.forecastTemp)
            forecastHumidity = itemView.findViewById(R.id.forecastHumidity)
            forecastWindSpeed = itemView.findViewById(R.id.forecastWindSpeed)
            forecastWindDeg = itemView.findViewById(R.id.forecastWindDeg)
            forecastWindGust = itemView.findViewById(R.id.forecastWindGust)
        }

    }
}
