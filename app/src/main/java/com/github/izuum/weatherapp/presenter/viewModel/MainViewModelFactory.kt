package com.github.izuum.weatherapp.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.izuum.weatherapp.data.repository.WeatherRepositoryImpl
import com.github.izuum.weatherapp.domain.usecase.GetForecastForDay

class MainViewModelFactory : ViewModelProvider.Factory {

    private val weatherRepository by lazy {
        WeatherRepositoryImpl()
    }

    private val getForecastForDay by lazy {
        GetForecastForDay(weatherRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getForecastForDay) as T
    }
}