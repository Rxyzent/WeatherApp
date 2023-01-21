package com.rxyzent.weatherapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.rxyzent.weatherapp.core.Db.Cache
import com.rxyzent.weatherapp.core.adapter.ForecastWeatherAdapter
import com.rxyzent.weatherapp.core.model.CityData
import com.rxyzent.weatherapp.core.model.CurrentWeather.CurrentWeatherData
import com.rxyzent.weatherapp.core.model.DailyWeather.DailyWeatherData
import com.rxyzent.weatherapp.core.network.NetworkConnection
import com.rxyzent.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = ForecastWeatherAdapter()
    private lateinit var currentData :CurrentWeatherData
    private lateinit var dailyData:DailyWeatherData
    private lateinit var data:ArrayList<CityData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Cache.cache.getBool()){
            intent = Intent(this@MainActivity,CitySelectActivity::class.java)
            startActivity(intent)
        }else{
            data = Cache.cache.getData()
        }
        binding.forecastList.adapter = adapter
        binding.forecastList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.editLocation.setOnClickListener {
            intent = Intent(this@MainActivity,CitySelectActivity::class.java)
            startActivity(intent)
        }
        binding.swipeLayout.setOnRefreshListener {
            Cache.cache.getData()[0].let {
                loadCurrentData(it.lat,it.lon, key = "4ca0685698b53d921f7371bad3cb8ce8","metric" )
                loadForecastData(it.lat,it.lon, key = "4ca0685698b53d921f7371bad3cb8ce8","metric" )
                binding.cityName.text = it.name
            }
        }

        data?.let{data->
            Cache.cache.getData()[0].let {
                loadCurrentData(it.lat,it.lon, key ="4ca0685698b53d921f7371bad3cb8ce8","metric" )
                loadForecastData(it.lat,it.lon, key = "4ca0685698b53d921f7371bad3cb8ce8","metric" )
                binding.cityName.text = it.name
            }
        }

        binding.changeLocation.setOnClickListener {
            val dialog  = CityChangeDialog(this@MainActivity)
            dialog.show()
            dialog.adapter.dialogListItemClick={
                loadCurrentData(it.lat,it.lon,"4ca0685698b53d921f7371bad3cb8ce8","metric")
                loadForecastData(it.lat,it.lon, "4ca0685698b53d921f7371bad3cb8ce8","metric")
                binding.cityName.text = it.name
                dialog.dismiss()
            }
        }
    }

    private fun loadForecastData(lat: Double,lon: Double,key: String,units: String){
        val call = NetworkConnection.getApiMethods().getDailyWeather(lat,lon,key,units)
        call.enqueue(object :Callback<DailyWeatherData>{
            override fun onResponse(
                call: Call<DailyWeatherData>,
                response: Response<DailyWeatherData>,
            ) {
                if(response.isSuccessful){
                    binding.swipeLayout.isRefreshing = false
                    dailyData = response.body()!!
                    adapter.setForecastData(dailyData.list)
                }
            }

            override fun onFailure(call: Call<DailyWeatherData>, t: Throwable) {
                binding.swipeLayout.isRefreshing = false
                if(t is UnknownHostException){
                    Toast.makeText(this@MainActivity, "Internet ulanmagan", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
    private fun loadCurrentData(lat: Double, lon: Double, key: String, units: String) {

        val call = NetworkConnection.getApiMethods().getCurrentWeather(lat,lon,key,units)
        call.enqueue(object :Callback<CurrentWeatherData>{
            override fun onResponse(
                call: Call<CurrentWeatherData>,
                response: Response<CurrentWeatherData>,
            ) {
                if(response.isSuccessful){
                    binding.swipeLayout.isRefreshing = false
                    currentData = response.body()!!

                    val dateFormat = SimpleDateFormat("E MMM dd,yyyy")
                    val timeFormat = SimpleDateFormat("HH:mm")
                    val dateString = dateFormat.format(currentData.dt.toLong())
                    val timeString = timeFormat.format(currentData.dt.toLong())
                    var directionStr = ""
                    if(currentData.wind.deg>45&&currentData.wind.deg<135){
                        directionStr = "East "
                    }else if(currentData.wind.deg>135&&currentData.wind.deg<225){
                        directionStr = "South "
                    }else if(currentData.wind.deg>225&&currentData.wind.deg<315){
                        directionStr = "West "
                    }else {
                        directionStr = "North "
                    }

                    binding.date.text = dateString
                    binding.time.text = timeString
                    binding.temperature.text = currentData.main.temp.toInt().toString()
                    binding.description.text =  currentData.weather[0].main
                    binding.max.text = currentData.main.temp_max.toInt().toString()
                    binding.min.text = currentData.main.temp_min.toInt().toString()
                    binding.wind.text = directionStr.plus(currentData.wind.speed.toString()).plus(" k/h")
                    val strIcon = "http://openweathermap.org/img/wn/".plus(currentData.weather[0].icon).plus("@2x.png")
                    binding.image.load(strIcon)

                    binding.humidity.text = currentData.main.humidity.toString().plus("%")
                    binding.pressure.text = currentData.main.pressure.toString().plus(" mbar")
                    binding.feelsLike.text = currentData.main.feels_like.roundToInt().toString().plus("°C")
                    binding.cloudCover.text = currentData.clouds.all.toString().plus("%")
                    binding.windSpeed.text = currentData.wind.speed.toString().plus(" k/h")
                    binding.sunrise.text = timeFormat.format(currentData.sys.sunrise)
                    binding.sunset.text = timeFormat.format(currentData.sys.sunset)
                    binding.detailImage.load(strIcon)
                    Log.d("IconUrl", "onResponse: $strIcon")
                }
            }

            override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                binding.swipeLayout.isRefreshing = false
                if(t is UnknownHostException){
                    Toast.makeText(this@MainActivity, "Internet ulanmagan", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}