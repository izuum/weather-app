package com.github.izuum.weatherapp.domain.models

data class WeatherDataFromDomain(
    var location: Location,
    var current: Current
)

data class Location(
    var name: String
)

data class Current(
    var last_updated: String,
    var temp_c: Float,
    var condition: Condition
)

data class Condition(
    var text: String,
    var icon: String
)

