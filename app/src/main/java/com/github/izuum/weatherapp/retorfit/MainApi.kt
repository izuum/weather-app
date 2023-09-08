package com.github.izuum.weatherapp.retorfit

import com.github.izuum.weatherapp.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("current.json")
    suspend fun getWeatherData(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("aqi") aqi: String,
        @Query("lang") lang: String
    ): WeatherData
}