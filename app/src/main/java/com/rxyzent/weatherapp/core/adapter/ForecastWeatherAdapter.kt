package com.rxyzent.weatherapp.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rxyzent.weatherapp.core.model.DailyWeather.ListData
import com.rxyzent.weatherapp.databinding.ForecastWeatherItemBinding
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class ForecastWeatherAdapter:RecyclerView.Adapter<ForecastViewHolder>() {

    var data = ArrayList<ListData>()

        fun setForecastData(data:List<ListData>){
            this.data.addAll(data)
            notifyItemRangeInserted(this.data.size-data.size,data.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(ForecastWeatherItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
       var tempMin:Double = 0.0
        var tempMax:Double = 0.0
        for(i in 0 until 40){
            if(i%8==0){
                tempMin = data[i].main.temp_min
                tempMax = data[i].main.temp_max
                for(j in i+1 until i+8){
                    if(tempMin>data[j].main.temp_min){
                        tempMin = data[j].main.temp_min
                    }
                    if(tempMax<data[j].main.temp_max){
                        tempMax = data[j].main.temp_max
                    }
                }
                holder.bindData(data[position],tempMin.roundToInt(),tempMax.roundToInt())
            }
        }
    }

    override fun getItemCount(): Int = data.size/8
}

class ForecastViewHolder(val binding: ForecastWeatherItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bindData(data:ListData,min:Int,max:Int){
        val dayFormat = SimpleDateFormat("E")
        val dayStr = dayFormat.format(data.dt)
        binding.day.text = dayStr
        binding.min.text = min.toString()
        binding.max.text = max.toString()
        binding.rainPer.text = data.clouds.all.toString().plus("%")
        val strIcon = "http://openweathermap.org/img/wn/".plus(data.weather[0].icon).plus("@2x.png")
        binding.image.load(strIcon)

        val param = binding.diagramm.layoutParams
        param.height = (max-min)*25
    }
}