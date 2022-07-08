package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.RetrofitInstance
import com.example.weatherapp.models.current.Current

class Repository {
    suspend fun getCurrentWeather(city: String, lang: String): Current {
        return RetrofitInstance.api.getCurrentWeather(city, lang)
    }
}