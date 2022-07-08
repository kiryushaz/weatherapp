package com.example.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.models.current.Current

class MainViewModel : ViewModel() {
    private val weatherData: MutableLiveData<Current>

}