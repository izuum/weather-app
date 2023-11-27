package com.github.izuum.weatherapp.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.izuum.weatherapp.domain.models.WeatherDataFromDomain
import com.github.izuum.weatherapp.domain.usecase.GetForecastForDay
import kotlinx.coroutines.launch

class MainViewModel(
    private val getForecastForDay: GetForecastForDay
) : ViewModel() {

    private val forecastForDayLiveData = MutableLiveData<WeatherDataFromDomain>()
    val forecastLive: LiveData<WeatherDataFromDomain> get() = forecastForDayLiveData

    fun getWeather() {
        try {
            viewModelScope.launch {
                val weather: WeatherDataFromDomain = getForecastForDay.execute()
                forecastForDayLiveData.value = weather
            }
        } catch (e: Exception){
            e.message
        }
    }
}