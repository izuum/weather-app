package com.github.izuum.weatherapp.data.repository

import com.github.izuum.weatherapp.data.liveData.coordinates
import com.github.izuum.weatherapp.data.models.WeatherData
import com.github.izuum.weatherapp.data.retrofit.Retrofit
import com.github.izuum.weatherapp.data.retrofit.Retrofit.API_KEY
import com.github.izuum.weatherapp.domain.models.WeatherDataFromDomain
import com.github.izuum.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getForecast(): WeatherDataFromDomain {
        val weather: WeatherData = Retrofit.api.getWeatherData(
            API_KEY,
            coordinates.value.toString(),
            "no",
            "ru"
        )
        return mapToWeatherDataFromDomain(weather)
    }
}

fun mapToWeatherDataFromDomain(weatherData: WeatherData): WeatherDataFromDomain {
    return WeatherDataFromDomain(
        name = weatherData.location.name,
        lastUpdated = weatherData.current.lastUpdated,
        tempC = weatherData.current.tempC,
        text = weatherData.current.condition.text,
        icon = weatherData.current.condition.icon
    )
}