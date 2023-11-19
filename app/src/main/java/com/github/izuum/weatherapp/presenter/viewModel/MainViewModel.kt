package com.github.izuum.weatherapp.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.izuum.weatherapp.domain.models.WeatherDataFromDomain
import com.github.izuum.weatherapp.domain.usecase.GetForecastForDay

class MainViewModel(
    private val getForecastForDay: GetForecastForDay
) : ViewModel() {

    private val forecastForDayLiveData = MutableLiveData<WeatherDataFromDomain>()
    val forecastLive: LiveData<WeatherDataFromDomain> = forecastForDayLiveData

    fun get() {
        val weather: WeatherDataFromDomain = getForecastForDay.execute()
        forecastForDayLiveData.value = weather
    }
}