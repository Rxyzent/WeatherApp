package com.rxyzent.weatherapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.rxyzent.weatherapp.core.model.CityData
import com.rxyzent.weatherapp.databinding.DialogListItemBinding

class DialogAdapter:BaseAdapter() {

    var dialogListItemClick : ((data:CityData)->Unit)? = null

    var data = ArrayList<CityData>()
    set(value) {
        field.addAll(value)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = data.size

    override fun getItem(p0: Int): CityData = data[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val data = getItem(p0)

        val binding = DialogListItemBinding.inflate(LayoutInflater.from(p2!!.context),p2,false)

        binding.text.text = data.name

        binding.root.setOnClickListener {
            dialogListItemClick?.invoke(data)
        }

        return binding.root
    }
}