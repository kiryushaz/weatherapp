package com.example.weatherapp.utils

import android.widget.ImageView
import com.example.weatherapp.R

class IconToUI constructor(icon: ImageView, img: String) {
    init {
        when (img) {
            "01d" -> icon.setImageResource(R.drawable.ic_sun)
            "01n" -> icon.setImageResource(R.drawable.ic_moon)
            "02d" -> icon.setImageResource(R.drawable.ic_day_cloud)
            "02n" -> icon.setImageResource(R.drawable.ic_night_cloud)
            "03d", "03n" -> icon.setImageResource(R.drawable.ic_scattered_cloud)
            "04d", "04n" -> icon.setImageResource(R.drawable.ic_overcast)
            "09d", "09n" -> icon.setImageResource(R.drawable.ic_shower_rain)
            "10d" -> icon.setImageResource(R.drawable.ic_day_rain)
            "10n" -> icon.setImageResource(R.drawable.ic_night_rain)
            "11d", "11n" -> icon.setImageResource(R.drawable.ic_thunderstorm)
            "13d", "13n" -> icon.setImageResource(R.drawable.ic_snow)
            "50d", "50n" -> icon.setImageResource(R.drawable.ic_mist)
        }
    }
}