package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.models.current.Current
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val button: Button = findViewById(R.id.buttonGetWeather)

        val apiKey = "7936d24402bdbc3705a6ed91dbadb69a"

        button.setOnClickListener {
            val city = findViewById<EditText>(R.id.inputCityName).text.toString().trim()

            if (city.isEmpty()) {
                Toast.makeText(this, R.string.empty_string, Toast.LENGTH_LONG).show()
            } else {
                progressBar.visibility = View.VISIBLE

                val currentRequest = Repository().getCurrentWeather(
                    city, apiKey, "metric", "ru"
                )

                currentRequest.enqueue(object : Callback<Current> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Current>, response: Response<Current>) {
                        if (response.isSuccessful) {
                            val result = response.body()!!
                            val icon: ImageView = findViewById(R.id.weatherImage)

                            findViewById<TextView>(R.id.cityName)
                                .text = "${result.name}, ${result.sys.country}"
                            findViewById<TextView>(R.id.weather_temperature)
                                .text = "${result.main.temp.roundToInt()}°C"
                            findViewById<TextView>(R.id.weather_temperature_feels_like)
                                .text = "ощущается как ${result.main.feels_like.roundToInt()}°C"
                            findViewById<TextView>(R.id.weather_description)
                                .text = result.weather[0].description

                            when (result.weather[0].icon) {
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

                            Log.d("result", result.toString())

                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                response.errorBody()!!.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        progressBar.visibility = View.GONE
                    }

                    override fun onFailure(call: Call<Current>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            t.message, Toast.LENGTH_LONG
                        ).show()

                        progressBar.visibility = View.GONE
                    }
                })
            }
        }
    }
}