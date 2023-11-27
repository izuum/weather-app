package com.github.izuum.weatherapp.domain.repository

import com.github.izuum.weatherapp.domain.models.WeatherDataFromDomain

interface WeatherRepository {
   suspend fun getForecast(): WeatherDataFromDomain
}