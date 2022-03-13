package com.weather.app.presenter.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.weather.app.R
import com.weather.app.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val gson = Gson()

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private var searchText: String = ""
    private val searchCity by lazy {
        Runnable {
            viewModel.searchCity(searchText)
        }
    }

    private val searchCityAdapter by lazy { CityItemAdapter(this) }
    private val favoriteCityAdapter by lazy {
        CityItemAdapter(this, true) { (id, name) ->
            Toast.makeText(
                this@MainActivity,
                getString(R.string.removed_city_from_favorite, name),
                Toast.LENGTH_LONG
            ).show()
            viewModel.removeFromFavorite(id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                handler.removeCallbacks(searchCity)
                handler.postDelayed(searchCity, 300)
            }
        })
        binding.searchResult.adapter = searchCityAdapter
        binding.searchResult.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.searchResult.setHasFixedSize(true)

        binding.favoriteCities.adapter = favoriteCityAdapter
        binding.favoriteCities.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.favoriteCities.setHasFixedSize(true)

        viewModel.prePopulateDb.observe(this) {
            binding.loadingProgress.isVisible = !it
            binding.contentGroup.isVisible = it
        }
        viewModel.listCityResult.observe(this) {
            if (it.isNullOrEmpty()) {
                searchCityAdapter.submitData(emptyList())
            } else {
                searchCityAdapter.submitData(it)
            }
        }
        viewModel.favoriteCities.observe(this) {
            if (it.isNullOrEmpty()) {
                favoriteCityAdapter.submitData(emptyList())
                binding.favoriteCityHeader.isVisible = false
                binding.divider.isVisible = false
            } else {
                favoriteCityAdapter.submitData(it)
                binding.favoriteCityHeader.isVisible = true
                binding.divider.isVisible = true
            }
        }
    }
}
