package com.github.izuum.weatherapp.domain.usecase

import com.github.izuum.weatherapp.domain.models.WeatherDataFromDomain
import com.github.izuum.weatherapp.domain.repository.WeatherRepository

class GetForecastForDay(private val weatherRepository: WeatherRepository) {

    suspend fun execute(): WeatherDataFromDomain{
        return weatherRepository.getForecast()
    }
}