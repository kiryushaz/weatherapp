package com.example.weatherapp.data.api

import com.example.weatherapp.BuildConfig.APP_ID
import com.example.weatherapp.models.current.Current
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // https://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}&units={units}&lang={lang}
    @GET("data/2.5/weather?units=metric&appid=$APP_ID")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("lang") lang: String
    ): Current
}