package com.github.izuum.weatherapp.data.repository

import com.github.izuum.weatherapp.data.models.WeatherData
import com.github.izuum.weatherapp.data.networkApi.RequestWeather
import com.github.izuum.weatherapp.domain.models.Condition
import com.github.izuum.weatherapp.domain.models.Current
import com.github.izuum.weatherapp.domain.models.Location
import com.github.izuum.weatherapp.domain.models.WeatherDataFromDomain
import com.github.izuum.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val requestWeather: RequestWeather): WeatherRepository {
    override fun getForecast(): WeatherDataFromDomain {

        val weather: WeatherData = requestWeather.getWeather()

        return WeatherDataFromDomain(
            location = Location(weather.location.name),
            current = Current(
                last_updated = weather.current.last_updated,
                temp_c = weather.current.temp_c,
                condition = Condition(
                    text = weather.current.condition.text,
                    icon = weather.current.condition.icon
                )
            )
        )
    }
}