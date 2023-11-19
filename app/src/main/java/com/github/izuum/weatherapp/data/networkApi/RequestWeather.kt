package com.github.izuum.weatherapp.data.networkApi

import com.github.izuum.weatherapp.data.liveData.cityName
import com.github.izuum.weatherapp.data.liveData.weatherFromApiLiveData
import com.github.izuum.weatherapp.data.models.Condition
import com.github.izuum.weatherapp.data.models.Current
import com.github.izuum.weatherapp.data.models.Location
import com.github.izuum.weatherapp.data.models.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "804967b7a2934d2a965221051232602"

class RequestWeather {
    fun getWeather(): WeatherData {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        CoroutineScope(Dispatchers.Main).launch {
            val api = retrofit.create(MainApi::class.java)
            val modelData =
                api.getWeatherData(
                    API_KEY,
                    cityName.value.toString(),
                    "no",
                    "ru"
                )
            weatherFromApiLiveData.value = modelData
        }
        return WeatherData(
            location = Location(weatherFromApiLiveData.value?.location?.name ?: "null"),
            current = Current(
                last_updated = weatherFromApiLiveData.value?.current?.last_updated ?: "null",
                temp_c = weatherFromApiLiveData.value?.current?.temp_c ?: 0.0f,
                condition = Condition(
                    text = weatherFromApiLiveData.value?.current?.condition?.text ?: "null",
                    icon = weatherFromApiLiveData.value?.current?.condition?.icon ?: "null"
                )
            )
        )
    }
}