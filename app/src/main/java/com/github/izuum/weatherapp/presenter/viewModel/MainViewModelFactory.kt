package com.github.izuum.weatherapp.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.izuum.weatherapp.data.getLocation.GetLocation
import com.github.izuum.weatherapp.data.networkApi.RequestWeather
import com.github.izuum.weatherapp.data.repository.WeatherRepositoryImpl
import com.github.izuum.weatherapp.domain.usecase.GetForecastForDay
import com.github.izuum.weatherapp.presenter.fragments.MainFragment

class MainViewModelFactory(mainFragment: MainFragment) : ViewModelProvider.Factory {

    private val currentLocation by lazy {
        GetLocation(mainFragment.requireContext())
    }

    private val requestWeather by lazy {
        RequestWeather()
    }

    private val weatherRepository by lazy {
        WeatherRepositoryImpl(requestWeather, currentLocation)
    }

    private val getForecastForDay by lazy {
        GetForecastForDay(weatherRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getForecastForDay) as T
    }
}