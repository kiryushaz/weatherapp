package com.example.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.models.current.Current
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val weatherData = MutableLiveData<Current>()
    val weatherDataError = MutableLiveData<String>()

    @OptIn(DelicateCoroutinesApi::class)
    fun launch(city: String, lang: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Repository().getCurrentWeather(city, lang)
                weatherData.postValue(response)
            } catch (err: Exception) {
                weatherDataError.postValue(err.message)
            }
        }
    }
}