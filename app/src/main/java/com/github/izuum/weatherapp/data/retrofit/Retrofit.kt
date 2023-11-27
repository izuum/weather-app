package com.github.izuum.weatherapp.data.retrofit

import com.github.izuum.weatherapp.data.networkApi.MainApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    private val okHttpClient = OkHttpClient.Builder()
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: MainApi = retrofit.create(MainApi::class.java)

    const val API_KEY = "804967b7a2934d2a965221051232602"
}