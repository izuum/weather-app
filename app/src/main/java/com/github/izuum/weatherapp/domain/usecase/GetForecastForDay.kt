package com.github.izuum.weatherapp.domain.usecase

import com.github.izuum.weatherapp.domain.models.WeatherDataFromDomain
import com.github.izuum.weatherapp.domain.repository.WeatherRepository

class GetForecastForDay(private val weatherRepository: WeatherRepository) {

    fun execute(): WeatherDataFromDomain{
        return weatherRepository.getForecast()
    }
}