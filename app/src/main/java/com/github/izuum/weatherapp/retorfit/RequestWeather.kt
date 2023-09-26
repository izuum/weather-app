package com.github.izuum.weatherapp.retorfit

import android.util.Log
import com.github.izuum.weatherapp.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val API_KEY = "804967b7a2934d2a965221051232602"

class RequestWeather {
    fun getWeather(viewModel: MainViewModel) {
        Log.d("getWeather", "Started")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        CoroutineScope(Dispatchers.Main).launch {
            val api = retrofit.create(MainApi::class.java)
            val modelData =
                api.getWeatherData(
                    API_KEY,
                    viewModel.cityName.value.toString(),
                    "no",
                    "ru"
                )
            viewModel.liveDataCurrent.value = modelData
        }
    }
}