package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.utils.IconToUI
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar: ProgressBar = binding.progressBar
        val button: Button = binding.buttonGetWeather

        val systemLanguage = Locale.getDefault().language

        button.setOnClickListener {
            val city = binding.inputCityName.text.toString().trim()

            if (city.isEmpty()) {
                Toast.makeText(this, R.string.empty_string, Toast.LENGTH_LONG).show()
            } else {
                progressBar.visibility = View.VISIBLE

                val repo = Repository()

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val result = repo.getCurrentWeather(city, systemLanguage)

                        withContext(Dispatchers.Main) {
                            binding.cityName.text = "${result.name}, ${result.sys.country}"
                            binding.weatherTemperature.text = "${result.main.temp.roundToInt()}°C"
                            binding.weatherTemperatureFeelsLike.text = "ощущается как ${result.main.feelsLike.roundToInt()}°C"
                            binding.weatherDescription.text = result.weather[0].description

                            IconToUI(binding.weatherImage, result.weather[0].icon)

                            progressBar.visibility = View.GONE
                        }
                    } catch (err: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, err.message, Toast.LENGTH_LONG).show()

                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}