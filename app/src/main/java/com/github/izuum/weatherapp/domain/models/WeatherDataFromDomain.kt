package com.github.izuum.weatherapp.domain.models

data class WeatherDataFromDomain(
    var name: String,
    var lastUpdated: String,
    var tempC: Float,
    var text: String,
    var icon: String
)



