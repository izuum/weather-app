package com.github.izuum.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData<WeatherData>()
    val cityName = MutableLiveData<String>()
}