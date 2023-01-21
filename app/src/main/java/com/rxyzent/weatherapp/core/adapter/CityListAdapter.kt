package com.rxyzent.weatherapp.core.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.rxyzent.weatherapp.core.Db.Cache
import com.rxyzent.weatherapp.core.model.CityData
import com.rxyzent.weatherapp.databinding.ListItemBinding

class CityListAdapter:BaseAdapter() {

    var listItemClick:((data:CityData)->Unit)?=null
    var data = ArrayList<CityData>()
    set(value) {
        field.addAll(value)
        notifyDataSetChanged()
    }
    override fun getCount(): Int = data.size

    override fun getItem(p0: Int): CityData = data[p0]

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val data = getItem(p0)

        val binding = ListItemBinding.inflate(LayoutInflater.from(p2!!.context),p2,false)

        binding.text.text = data.name
        if(!data.isChecked){
            binding.selectIcon.setCardBackgroundColor(Color.parseColor("#888888"))
        }else {
            binding.selectIcon.setCardBackgroundColor(Color.parseColor("#7B1FA2"))
        }

        binding.root.setOnClickListener {
            if(!data.isChecked){
                binding.selectIcon.setCardBackgroundColor(Color.parseColor("#7B1FA2"))
                data.isChecked = true
            }else {
                binding.selectIcon.setCardBackgroundColor(Color.parseColor("#888888"))
                data.isChecked = false
            }

            listItemClick?.invoke(data)
        }

        return binding.root

    }
}