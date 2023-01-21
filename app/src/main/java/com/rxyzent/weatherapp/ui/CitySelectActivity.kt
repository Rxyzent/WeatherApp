package com.rxyzent.weatherapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rxyzent.weatherapp.core.Db.Cache
import com.rxyzent.weatherapp.core.adapter.CityListAdapter
import com.rxyzent.weatherapp.core.model.CityData
import com.rxyzent.weatherapp.databinding.ActivityCitySelectBinding

class CitySelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCitySelectBinding
    private val adapter = CityListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedData = ArrayList<CityData>()


        var data = ArrayList<CityData>()
        data.addAll(
            arrayListOf(
                CityData(41.311158, 69.279737, "Tashkent",0),
                CityData(40.783388, 72.350663, "Andijan",1),
                CityData(39.767966, 64.421728, "Bukhara",2),
                CityData(40.389420, 71.783009, "Fergana",3),
                CityData(40.120295, 67.828517, "Jizzakh",4),
                CityData(41.550438, 60.631494, "Xorazm",5),
                CityData(41.000085, 71.672579, "Namangan",6),
                CityData(40.103093, 65.373970, "Navoi",7),
                CityData(38.841612, 65.789988, "Qashqadaryo",8),
                CityData(39.654404, 66.975827, "Samarqand",9),
                CityData(40.838844, 68.664229, "Sirdaryo",10),
                CityData(37.228574, 67.275460, "Surxondaryo",11),
                CityData(42.460341, 59.617996, "Karakalpakstan",12)
            )
        )

        if(!Cache.cache.getBool()){
            selectedData = Cache.cache.getData()

                for(i in 0 until data.size){
                    for(j in 0 until selectedData.size){
                        if(data[i].id == selectedData[j].id){
                            data[i] = selectedData[j]
                        }
                    }
                }
        }
        Cache.cache.saveBool(false)


        binding.cityList.adapter = adapter
        adapter.data = data
        adapter.listItemClick = {
            if(it.isChecked){
                selectedData.add(it)
            }else{
                selectedData.remove(it)
            }

        }
        binding.nextBtn.setOnClickListener {
            Log.d("ArrayData", "onCreate: ${selectedData.size}")
            Cache.cache.clearData()
            Cache.cache.saveData(selectedData)
            if(selectedData.isEmpty()){
                Toast.makeText(this@CitySelectActivity, "Select city", Toast.LENGTH_SHORT).show()
            }else{
                finish()
            }
        }

    }
}