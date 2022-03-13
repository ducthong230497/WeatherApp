package com.weather.app.presenter.main

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.R
import com.weather.app.domain.entities.City
import com.weather.app.presenter.weather_detail.WeatherDetailActivity

class CityItemAdapter(
    private val activity: Activity,
    private val addIcon: Boolean = false,
    private val onIconClick: ((data: Pair<Long, String>) -> Unit)? = null
): RecyclerView.Adapter<CityItemAdapter.ViewHolder>() {

    private var listCity: List<City> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_city_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = listCity[position]
        holder.cityName.text = city.name
        if (addIcon) {
            holder.favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            holder.favoriteIcon.setImageResource(0)
        }
        if (onIconClick != null) {
            holder.favoriteIcon.setOnClickListener { onIconClick.invoke(Pair(city.id, city.name ?: "")) }
        } else {
            holder.favoriteIcon.setOnClickListener(null)
        }
        holder.view.setOnClickListener {
            WeatherDetailActivity.startActivity(activity, cityId = city.id)
        }
    }

    override fun getItemCount(): Int {
        return listCity.size
    }

    fun submitData(listCity: List<City>) {
        this.listCity = listCity
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder (view) {
        val cityName: AppCompatTextView = itemView.findViewById(R.id.cityName)
        val favoriteIcon: AppCompatImageView = itemView.findViewById(R.id.favoriteIcon)
    }
}
