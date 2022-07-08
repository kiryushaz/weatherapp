package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.utils.IconToUI
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val time = SimpleDateFormat("HH:mm")
        time.timeZone = TimeZone.getTimeZone("UTC")

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mViewModel.weatherData.observe(this) {
            binding.cityName.text = it.name
            binding.updateTime.text = SimpleDateFormat("EEE, d MMM HH:mm")
                .format((it.dt).toLong() * 1000).toString()
            binding.weatherTemperature.text = "${it.main.temp.roundToInt()}°C"
            binding.weatherTemperatureFeelsLike
                .text = "ощущается как ${it.main.feelsLike.roundToInt()}°C"
            binding.weatherDescription.text = it.weather[0].description

            binding.tvDataHumidity.text = "${it.main.humidity}%"
            binding.tvDataWindSpeed.text = "${it.wind.speed} м/с"
            binding.tvDataPressure.text = "${(it.main.pressure * 0.75).roundToInt()} мм.рт.ст."
            binding.tvDataSunrise.text =
                time.format((it.sys.sunrise + it.timezone).toLong() * 1000).toString()
            binding.tvDataSunset.text =
                time.format((it.sys.sunset + it.timezone).toLong() * 1000).toString()
            IconToUI(binding.weatherImage, it.weather[0].icon)

            binding.weatherContainerExtra.visibility = View.VISIBLE
        }

        mViewModel.weatherDataError.observe(this) {
            Toast.makeText(this, "Ошибка: $it", Toast.LENGTH_LONG).show()
        }

        val progressBar: ProgressBar = binding.progressBar
        val systemLanguage = Locale.getDefault().language

        binding.buttonGetWeather.setOnClickListener {
            val city = binding.inputCityName.text.toString().trim()

            if (city.isEmpty()) {
                Toast.makeText(this, R.string.empty_string, Toast.LENGTH_LONG).show()
            } else {
                progressBar.visibility = View.VISIBLE

                mViewModel.launch(city, systemLanguage)

                progressBar.visibility = View.GONE
            }
        }
    }
}