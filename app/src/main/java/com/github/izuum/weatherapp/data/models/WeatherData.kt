package com.github.izuum.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("location")
    var location: Location,
    @SerializedName("current")
    var current: Current
)

data class Location(
    @SerializedName("name")
    var name: String
)

data class Current(
    @SerializedName("last_updated")
    var lastUpdated: String,
    @SerializedName("temp_c")
    var tempC: Float,
    @SerializedName("condition")
    var condition: Condition
)

data class Condition(
    @SerializedName("text")
    var text: String,
    @SerializedName("icon")
    var icon: String
)