package com.rxyzent.weatherapp.core.Db

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxyzent.weatherapp.core.model.CityData
import com.rxyzent.weatherapp.core.model.SelectedData

class Cache private constructor(context: Context){

    private val preferences:SharedPreferences

    init {
        preferences = context.getSharedPreferences("cache",Context.MODE_PRIVATE)
    }

    fun getBool():Boolean{
        return preferences.getBoolean("bool",true)
    }
    fun saveBool(b:Boolean){
        preferences.edit().putBoolean("bool",b).apply()
    }

    fun clearData(){
        preferences.edit().remove("selected_data").apply()
    }
    fun getSavedD(id:Int):CityData{
        val lat = preferences.getFloat("lat"+id, 0.0F).toDouble()
        val lon = preferences.getFloat("lon"+id,0.0F).toDouble()
        val name = preferences.getString("name"+id,"")

        return CityData(lat,lon,name, id)
    }
    fun saveData(data:ArrayList<CityData>){
        val selectedData = Gson().toJson(data)

        preferences.edit().putString("selected_data",selectedData).apply()
    }
    fun getData():ArrayList<CityData>{
        val selectedDataStr = preferences.getString("selected_data","")
        val selectedData =  Gson().fromJson(selectedDataStr,SelectedData::class.java)

        return selectedData
    }
    companion object {
        lateinit var cache: Cache
        fun init(context: Context){
            cache = Cache(context)
        }
    }
}