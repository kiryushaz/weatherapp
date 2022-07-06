package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.RetrofitInstance
import com.example.weatherapp.models.current.Current
import retrofit2.Call

class Repository {
    fun getCurrentWeather(city: String, key: String, units: String, lang: String): Call<Current> {
        return RetrofitInstance.api.getCurrentWeather(city, key, units, lang)
    }
}