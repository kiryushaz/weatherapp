package com.example.weatherapp.data.api

import com.example.weatherapp.models.current.Current
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // https://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}&units={units}&lang={lang}
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") key: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<Current>
}